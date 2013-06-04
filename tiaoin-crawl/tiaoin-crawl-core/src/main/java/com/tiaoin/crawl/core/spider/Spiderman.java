package com.tiaoin.crawl.core.spider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.tiaoin.crawl.common.exception.ServiceException;
import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.component.collection.SpiderComponentCollectionServiceImpl;
import com.tiaoin.crawl.core.listener.DefaultSpiderListerner;
import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.manager.PluginManager;
import com.tiaoin.crawl.core.manager.SiteManager;
import com.tiaoin.crawl.core.plugin.BeginPoint;
import com.tiaoin.crawl.core.plugin.DigPoint;
import com.tiaoin.crawl.core.plugin.DoneException;
import com.tiaoin.crawl.core.plugin.DupRemovalPoint;
import com.tiaoin.crawl.core.plugin.EndPoint;
import com.tiaoin.crawl.core.plugin.ExtensionPoint;
import com.tiaoin.crawl.core.plugin.ExtensionPoints;
import com.tiaoin.crawl.core.plugin.FetchPoint;
import com.tiaoin.crawl.core.plugin.ParsePoint;
import com.tiaoin.crawl.core.plugin.Point;
import com.tiaoin.crawl.core.plugin.PojoPoint;
import com.tiaoin.crawl.core.plugin.TargetPoint;
import com.tiaoin.crawl.core.plugin.TaskPollPoint;
import com.tiaoin.crawl.core.plugin.TaskPushPoint;
import com.tiaoin.crawl.core.plugin.TaskSortPoint;
import com.tiaoin.crawl.core.task.SpiderTimerTask;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.task.TaskQueue;
import com.tiaoin.crawl.core.xml.Plugin;
import com.tiaoin.crawl.core.xml.Site;
import com.tiaoin.crawl.core.xml.Target;
//import com.tiaoin.crawl.core.infra.SpiderIOC;
//import com.tiaoin.crawl.core.infra.SpiderIOCs;

public class Spiderman {

    public Boolean           isStop           = false;
    public Boolean           isShutdownNow    = false;
    private Boolean          isInit           = false;
    private Boolean          initSuccess      = false;
    private ExecutorService  pool             = null;
    private Collection<Site> sites            = null;

    private boolean          isSchedule       = false;
    private Timer            timer            = new Timer();
    private String           scheduleTime     = "1h";
    private String           scheduleDelay    = "1m";
    private int              scheduleTimes    = 0;
    private int              maxScheduleTimes = 0;

    private SpiderListener   spiderListener;
    @Resource
    private PluginManager pluginManager;
    @Resource
    private SiteManager siteManager;
    @Resource
    private SpiderComponentCollectionServiceImpl spiderComponentCollectionService;
    
    public SpiderListener getSpiderListener() {
        return spiderListener;
    }

    /**
     * @date 2013-1-17 下午01:43:52
     * @param listener
     * @return
     */
    public void init(SpiderListener listener) {
        this.spiderListener = listener;
        init();
    }

    /**
     * 爬虫初始化,加载组件
     */
    public void init() {
        if (this.spiderListener == null)
            this.spiderListener = new DefaultSpiderListerner();
        isStop = false;
        isShutdownNow = false;
        sites = null;
        pool = null;
        try {
            spiderComponentCollectionService.init();
            siteManager.doInit(spiderComponentCollectionService);
            pluginManager.doInit(spiderComponentCollectionService);
            sites = spiderComponentCollectionService.getCompoentDescriptor();
            //initSites();
            initPool();
            isInit = true;
            initSuccess = true;
        } catch (ServiceException e) {
            spiderListener.onInfo(Thread.currentThread(), null, "Spiderman init error.");
            spiderListener.onError(Thread.currentThread(), null, "Spiderman init error.", e);
        }
    }

    public void startup() {
        if (!isInit) {
            init();
        }
        if(initSuccess) {
            if (isSchedule) {
                final Spiderman _this = this;
                timer.schedule(new SpiderTimerTask(null), new Date(),
                    (StringUtil.toSeconds(scheduleTime).intValue() + StringUtil
                        .toSeconds(scheduleDelay).intValue()) * 1000);
            } else {
                _startup();
            }
        } else {
            spiderListener.onInfo(Thread.currentThread(), null, "Spiderman init failed,stop spider."); 
        }

    }

    private void _startup() {
        for (Site site : sites) {
            pool.execute(new Spiderman._Executor(site));
            spiderListener.onInfo(Thread.currentThread(), null, "spider tasks of site[" + site.getName()
                                                          + "] start... ");
        }
    }

    // -------- Schedule ------------

    public void blocking() {
        while (isSchedule) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否超过调度次数
     * 
     * @return
     */
    public boolean outOfSchedule() {
        if (maxScheduleTimes > 0 && scheduleTimes >= maxScheduleTimes) {
            return true;
        }
        return false;
    }

    /**
     * 调用后开始
     */
    public void startSchedule() {
        // 阻塞，判断之前所有的网站是否都已经停止完全
        // 加个超时
        long start = System.currentTimeMillis();
        long timeout = 10 * 60 * 1000;
        while (true) {
            if ((System.currentTimeMillis() - start) > timeout) {
                spiderListener.onError(Thread.currentThread(), null,
                    "timeout of restart blocking check...", new Exception());
                break;
            }
            if (sites == null || sites.isEmpty())
                break;
            try {
                Thread.sleep(1 * 1000);
                boolean canBreak = true;
                for (Site site : sites) {
                    if (!site.isStop) {
                        canBreak = false;
                        spiderListener.onInfo(Thread.currentThread(), null,
                            "can not restart spiderman cause there has running-tasks of this site -> "
                                    + site.getName() + "...");
                    }
                }

                if (canBreak)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        // 只有所有的网站资源都已被释放[特殊情况timeout]完全才重启Spiderman
        scheduleTimes++;
        String strTimes = scheduleTimes + "";
        if (maxScheduleTimes > 0)
            strTimes += "/" + maxScheduleTimes;

        spiderListener.onInfo(Thread.currentThread(), null, "Spiderman has scheduled " + strTimes
                                                      + " times.");
        _startup();
        keepStrict(scheduleTime);
    }

    //    public Spiderman schedule() {
    //        return schedule(null);
    //    }
    //
    //    public Spiderman schedule(String time) {
    //        if (time != null && time.trim().length() > 0)
    //            this.scheduleTime = time;
    //        this.isSchedule = true;
    //        return this;
    //    }

    public void delay(String delay) {
        if (delay != null && delay.trim().length() > 0)
            this.scheduleDelay = delay;
    }

    public void times(int maxTimes) {
        if (maxTimes > 0)
            this.maxScheduleTimes = maxTimes;
    }

    /**
     * 取消调度
     * 
     * @return
     */
    public void cancel() {
        timer.cancel();
        timer = new Timer();
        spiderListener.onInfo(Thread.currentThread(), null,
            "Spiderman has completed and cancel the schedule.");
        isSchedule = false;
    }

    // ------------------------------

    public void keepStrict(String time) {
        keepStrict(StringUtil.toSeconds(time).longValue() * 1000);
    }

    public void keepStrict(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
        shutdownNow();
    }

    public void keep(String time) {
        keep(StringUtil.toSeconds(time).longValue() * 1000);
    }

    public void keep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
        shutdown();
    }

    public void shutdown() {
        pool.shutdown();
        pool = null;
        isStop = true;
    }

    public void shutdownNow() {
        pool.shutdownNow();
        pool = null;
        isStop = true;
        isShutdownNow = true;
    }

    /*private void loadPlugins() throws Exception {
        File siteFolder = new File(Settings.getSiteXmlFolder());
        if (!siteFolder.exists())
            throw new Exception("can not found WebSites folder -> " + siteFolder.getAbsolutePath());

        if (!siteFolder.isDirectory())
            throw new Exception("WebSites -> " + siteFolder.getAbsolutePath() + " must be folder !");

        File[] files = siteFolder.listFiles();
        if (files == null || files.length == 0) {
            String sitePath = siteFolder.getAbsoluteFile() + File.separator + "_site_sample_.xml";
            File file = new File(sitePath);
            Site site = new Site();

            Plugins plugins = new Plugins();
            plugins.getPlugin().add(pluginManager.createPlugin());
            site.setPlugins(plugins);

            XStreamUtil.transferBean2Xml(sitePath, site);
        }

        sites = new ArrayList<Site>(files.length);
        for (File file : files) {
            if (!file.exists())
                continue;
            if (!file.isFile())
                continue;
            if (!file.getName().endsWith(".xml"))
                continue;
            Beans beans = new Beans();
            XStreamUtil.transferXml2Bean(file.getAbsolutePath(), beans);
            Site site = beans.getSite();
            if (site == null)
                throw new Exception("site xml file error -> " + file.getAbsolutePath());
            if ("1".equals(site.getEnable())) {
                sites.add(site);
            }
        }
    }*/

    /*private void initSites() throws Exception {
        for (Site site : sites) {
            if (site.getName() == null || site.getName().trim().length() == 0)
                throw new Exception("site name required");
            if (site.getUrl() == null || site.getUrl().trim().length() == 0)
                throw new Exception("site url required");
            if (site.getTargets() == null || site.getTargets().getTarget().isEmpty())
                throw new Exception("site target required");

            List<Target> targets = site.getTargets().getTarget();
            if (targets == null || targets.isEmpty())
                throw new Exception("can not get any url target of site -> " + site.getName());

            // ---------------------插件初始化开始----------------------------
            spiderListener.onInfo(Thread.currentThread(), null, "plugins loading begin...");
            //Collection<Plugin> plugins = site.getPlugins().getPlugin();
            // 加载网站插件配置
            try {
                //pluginManager.loadPluginConf(plugins, spiderListener);

                // 加载TaskPoll扩展点实现类
                ExtensionPoint<TaskPollPoint> taskPollPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.task_poll);
                if (taskPollPoint != null) {
                    site.taskPollPointImpls = taskPollPoint.getExtensions();
                    firstInitPoint(site.taskPollPointImpls, site, spiderListener);
                }

                // 加载Begin扩展点实现类
                ExtensionPoint<BeginPoint> beginPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.begin);
                if (beginPoint != null) {
                    site.beginPointImpls = beginPoint.getExtensions();
                    firstInitPoint(site.beginPointImpls, site, spiderListener);
                }

                // 加载Fetch扩展点实现类
                ExtensionPoint<FetchPoint> fetchPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.fetch);
                if (fetchPoint != null) {
                    site.fetchPointImpls = fetchPoint.getExtensions();
                    firstInitPoint(site.fetchPointImpls, site, spiderListener);
                }

                // 加载Dig扩展点实现类
                ExtensionPoint<DigPoint> digPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.dig);
                if (digPoint != null) {
                    site.digPointImpls = digPoint.getExtensions();
                    firstInitPoint(site.digPointImpls, site, spiderListener);
                }

                // 加载DupRemoval扩展点实现类
                ExtensionPoint<DupRemovalPoint> dupRemovalPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.dup_removal);
                if (dupRemovalPoint != null) {
                    site.dupRemovalPointImpls = dupRemovalPoint.getExtensions();
                    firstInitPoint(site.dupRemovalPointImpls, site, spiderListener);
                }
                // 加载TaskSort扩展点实现类
                ExtensionPoint<TaskSortPoint> taskSortPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.task_sort);
                if (taskSortPoint != null) {
                    site.taskSortPointImpls = taskSortPoint.getExtensions();
                    firstInitPoint(site.taskSortPointImpls, site, spiderListener);
                }

                // 加载TaskPush扩展点实现类
                ExtensionPoint<TaskPushPoint> taskPushPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.task_push);
                if (taskPushPoint != null) {
                    site.taskPushPointImpls = taskPushPoint.getExtensions();
                    firstInitPoint(site.taskPushPointImpls, site, spiderListener);
                }

                // 加载Target扩展点实现类
                ExtensionPoint<TargetPoint> targetPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.target);
                if (targetPoint != null) {
                    site.targetPointImpls = targetPoint.getExtensions();
                    firstInitPoint(site.targetPointImpls, site, spiderListener);
                }

                // 加载Parse扩展点实现类
                ExtensionPoint<ParsePoint> parsePoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.parse);
                if (parsePoint != null) {
                    site.parsePointImpls = parsePoint.getExtensions();
                    firstInitPoint(site.parsePointImpls, site, spiderListener);
                }

                // 加载Pojo扩展点实现类
                ExtensionPoint<PojoPoint> pojoPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.pojo);
                if (pojoPoint != null) {
                    site.pojoPointImpls = pojoPoint.getExtensions();
                    firstInitPoint(site.pojoPointImpls, site, spiderListener);
                }

                // 加载End扩展点实现类
                ExtensionPoint<EndPoint> endPoint = pluginManager
                    .getExtensionPoint(ExtensionPoints.end);
                if (endPoint != null) {
                    site.endPointImpls = endPoint.getExtensions();
                    firstInitPoint(site.endPointImpls, site, spiderListener);
                }
                // ---------------------------插件初始化完毕----------------------------------
            } catch (Exception e) {
                throw new Exception("Site[" + site.getName() + "] loading plugins fail", e);
            }

            // 初始化网站的队列容器
            site.queue = new TaskQueue();
            site.queue.init();
            // 初始化网站目标Model计数器
            site.counter = new Counter();
        }
    }
*/
   /* private void firstInitPoint(Collection<? extends Point> points, Site site,
                                SpiderListener listener) {
        for (Point point : points) {
            point.init(site, listener);
        }
    }*/

    private void destroyPoint(Collection<? extends Point> points) {
        if (points == null)
            return;
        for (Point point : points) {
            try {
                point.destroy();
            } catch (DoneException e) {
                continue;
            }
            point = null;
        }
    }

    private void destroySite(Site site) {
        destroyPoint(site.beginPointImpls);
        destroyPoint(site.digPointImpls);
        destroyPoint(site.dupRemovalPointImpls);
        destroyPoint(site.endPointImpls);
        destroyPoint(site.fetchPointImpls);
        destroyPoint(site.parsePointImpls);
        destroyPoint(site.pojoPointImpls);
        destroyPoint(site.targetPointImpls);
        destroyPoint(site.taskPollPointImpls);
        destroyPoint(site.taskPushPointImpls);
        destroyPoint(site.taskSortPointImpls);
        site.queue.stop();
        site.isStop = true;
        if (isShutdownNow) {
            site.counter = null;
            site.fetcher = null;
            site = null;
        }
    }

    private void initPool() {
        if (pool == null) {
            int size = sites.size();
            if (size == 0)
                throw new RuntimeException("there is no website to fetch...");
            pool = new ThreadPoolExecutor(size, size, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());

            spiderListener.onInfo(Thread.currentThread(), null, "init thread pool size->" + size
                                                          + " success ");
        }
    }

    private class _Executor implements Runnable {
        private Site            site  = null;
        private ExecutorService _pool = null;

        public _Executor(Site site) {
            this.site = site;
            String strSize = site.getThread();
            int size = Integer.parseInt(strSize);
            spiderListener.onInfo(Thread.currentThread(), null, "site thread size -> " + size);
            RejectedExecutionHandler rejectedHandler = new RejectedExecutionHandler() {
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    // 拿到被弹出来的爬虫引用
                    Spider spider = (Spider) r;
                    try {
                        // 将该爬虫的任务 task 放回队列
                        spider.pushTask(Arrays.asList(spider.task));
                        String info = "repush the task->" + spider.task + " to the Queue.";
                        spider.listener.onError(Thread.currentThread(), spider.task, info,
                            new Exception(info));
                    } catch (Exception e) {
                        String err = "could not repush the task to the Queue. cause -> "
                                     + e.toString();
                        spider.listener.onError(Thread.currentThread(), spider.task, err, e);
                    }
                }
            };

            if (size > 0)
                this._pool = new ThreadPoolExecutor(size, size, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(), rejectedHandler);
            else
                this._pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), rejectedHandler);
        }

        public void run() {
            // 运行种子任务
            Task feedTask = new Task(new String(this.site.getUrl()), this.site, 10);
            Spider feedSpider = new Spider();
            feedSpider.init(feedTask);
            feedSpider.run();

            final float times = StringUtil.toSeconds(this.site.getSchedule()) * 1000;
            long start = System.currentTimeMillis();
            while (true) {
                try {
                    if (isStop) {
                        if (isShutdownNow)
                            _pool.shutdownNow();
                        else
                            _pool.shutdown();

                        _pool = null;
                        spiderListener.onInfo(Thread.currentThread(), null, site.getName()
                                                                      + ".Spider shutdown...");
                        destroySite(this.site);
                        return;
                    }

                    // 扩展点：TaskPoll
                    Task task = null;
                    Collection<TaskPollPoint> taskPollPoints = site.taskPollPointImpls;
                    if (taskPollPoints != null && !taskPollPoints.isEmpty()) {
                        for (TaskPollPoint point : taskPollPoints) {
                            task = point.pollTask();
                        }
                    }

                    if (task == null) {
                        long wait = StringUtil.toSeconds(site.getWaitQueue()).longValue();
                        // listener.onInfo(Thread.currentThread(), null,
                        // "queue empty wait for -> " + wait + " seconds");
                        if (wait > 0) {
                            try {
                                Thread.sleep(wait * 1000);
                            } catch (Exception e) {

                            }
                        }
                        continue;
                    }

                    Spider spider = new Spider();
                    spider.init(task);
                    _pool.execute(spider);

                } catch (DoneException e) {
                    spiderListener.onInfo(Thread.currentThread(), null, e.toString());
                    return;
                } catch (Exception e) {
                    spiderListener.onError(Thread.currentThread(), null, e.toString(), e);
                } finally {
                    long cost = System.currentTimeMillis() - start;
                    if (cost >= times) {
                        // 运行种子任务
                        feedSpider.run();
                        spiderListener
                            .onInfo(Thread.currentThread(), null, " shcedule FeedSpider per "
                                                                  + times + ", now cost time ->"
                                                                  + cost);
                        start = System.currentTimeMillis();// 重新计时
                    }
                }
            }
        }
    }

}
