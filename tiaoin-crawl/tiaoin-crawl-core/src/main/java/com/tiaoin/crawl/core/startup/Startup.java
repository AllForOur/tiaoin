package com.tiaoin.crawl.core.startup;

import java.util.Collection;

import javax.annotation.Resource;

import com.tiaoin.crawl.common.annotation.ValueAnnotation;
import com.tiaoin.crawl.common.enums.ValueType;
import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.component.AbstractComponent;
import com.tiaoin.crawl.core.component.collection.SpiderComponentCollectionServiceImpl;
import com.tiaoin.crawl.core.manager.PluginManager;
import com.tiaoin.crawl.core.manager.SiteManager;
import com.tiaoin.crawl.core.manager.ThreadPoolManager;
import com.tiaoin.crawl.core.schedule.Scheduler;
import com.tiaoin.crawl.core.service.StartupService;
import com.tiaoin.crawl.core.xml.Site;

/**
 * 标准的系统启动类
 * ***********************
 * 爬虫启动器,用于启动整个爬虫核心及子爬虫
 * 必须添加ValueAnnotation注解，不然不与加载
 * ***********************
 * 
 * @author sky.yang
 * @version $Id: Startup.java, v 1.0 2013-5-31 下午10:40:19 sky.yang Exp $
 */
@ValueAnnotation(type = ValueType.TYPE_INT, propertyName = "level", value = "1")
public class Startup extends AbstractComponent implements StartupService {

    @Resource
    private SpiderComponentCollectionServiceImpl spiderComponentCollectionService;
    @Resource
    private PluginManager                        pluginManager;
    @Resource
    private SiteManager                          siteManager;
    @Resource
    private ThreadPoolManager                    threadPoolManager;
    private Collection<Site>                     sites;
    @Resource
    private Scheduler                            scheduler;

    //是否调度
    private boolean                              isSchedule = false;

    @Override
    public void init() {
        spiderComponentCollectionService.init();
        sites = spiderComponentCollectionService.getCompoentDescriptor();
        siteManager.doInit(spiderComponentCollectionService);
        pluginManager.doInit(spiderComponentCollectionService);
        threadPoolManager.doInit(spiderComponentCollectionService);
    }

    /**
     * 爬虫启动方法,通过该方法启动爬虫
     */
    public void startup() {
        if (isSchedule) {
            scheduler.schedule(this);
        } else {
            _startup();
        }
    }

    /**
     * 判断是否能够开始调度
     * ======================
     * 阻塞，判断之前所有的网站是否都已经停止完全
     * 加个超时
     * ======================
     */
    public void canSchedule() {
        long start = System.currentTimeMillis();
        long timeout = 10 * 60 * 1000;
        while (true) {
            if ((System.currentTimeMillis() - start) > timeout) {
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
                    }
                }

                if (canBreak)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 爬虫启动入口,通过多线程启动每个配置爬虫
     */
    public void _startup() {
        for (Site site : sites) {
            threadPoolManager.execute(site);
        }
    }

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
        threadPoolManager.shutdown();
    }

    public void shutdownNow() {
        threadPoolManager.shutdownNow();
    }

    public void setSchedule(boolean isSchedule) {
        this.isSchedule = isSchedule;
    }
}
