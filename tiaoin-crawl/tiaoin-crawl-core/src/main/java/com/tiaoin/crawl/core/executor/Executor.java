package com.tiaoin.crawl.core.executor;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.manager.ThreadPoolManager;
import com.tiaoin.crawl.core.plugin.DoneException;
import com.tiaoin.crawl.core.plugin.Point;
import com.tiaoin.crawl.core.plugin.TaskPollPoint;
import com.tiaoin.crawl.core.spider.Spider;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class Executor implements Runnable {
    private static final Logger             logger = Logger.getLogger(Executor.class);
    private Site                            site;
    private ExecutorService                 executorPool;
    private ThreadPoolManager               threadPoolManager;
    @Resource
    private DefaultRejectedExecutionHandler defaultRejectedExecutionHandler;

    public void init(Site site, ThreadPoolManager threadPoolManager) {
        this.site = site;
        this.threadPoolManager = threadPoolManager;
        String strSize = site.getThread();
        int size = Integer.parseInt(strSize);
        if (size > 0) {
            executorPool = new ThreadPoolExecutor(size, size, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), defaultRejectedExecutionHandler);
        } else {
            executorPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), defaultRejectedExecutionHandler);
        }

    }

    @Override
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
                if (threadPoolManager.isStop()) {
                    if (threadPoolManager.isShutdownNow()) {
                        executorPool.shutdownNow();
                    } else {
                        executorPool.shutdown();
                    }
                    executorPool = null;
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
                executorPool.execute(spider);

            } catch (DoneException e) {
                logger.error(e);
                return;
            } catch (Exception e) {
                logger.error(e);
            } finally {
                long cost = System.currentTimeMillis() - start;
                if (cost >= times) {
                    // 运行种子任务
                    feedSpider.run();
                    start = System.currentTimeMillis();// 重新计时
                }
            }
        }
    }

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
        if (threadPoolManager.isShutdownNow()) {
            site.counter = null;
            site.fetcher = null;
            site = null;
        }
    }
}
