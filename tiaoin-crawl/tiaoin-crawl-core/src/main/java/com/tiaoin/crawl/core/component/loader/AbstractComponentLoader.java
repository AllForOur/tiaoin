package com.tiaoin.crawl.core.component.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tiaoin.crawl.common.exception.ServiceException;
import com.tiaoin.crawl.common.utils.XStreamUtil;
import com.tiaoin.crawl.core.spider.Counter;
import com.tiaoin.crawl.core.spider.Settings;
import com.tiaoin.crawl.core.task.TaskQueue;
import com.tiaoin.crawl.core.xml.Beans;
import com.tiaoin.crawl.core.xml.Site;

public class AbstractComponentLoader implements ComponentLoader<List<Site>> {
    private Logger logger = Logger.getLogger(AbstractComponentLoader.class);
    private List<Site> sites ;

    @Override
    public void loader() {
        //将 site配置从xml中加载进来
        //首先获取配置文件地址
        File siteFolder = new File(Settings.getSiteXmlFolder());
        if (!siteFolder.exists())
            throw new ServiceException("can not found WebSites folder -> " + siteFolder.getAbsolutePath());

        if (!siteFolder.isDirectory())
            throw new ServiceException("WebSites -> " + siteFolder.getAbsolutePath() + " must be folder !");
        
        File[] files = siteFolder.listFiles();
        if(null == files || files.length == 0) {
            throw new ServiceException("[INIT Exception][loader site xml config failed, xmls aren't exist!]");
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
            if (site == null) {
                throw new ServiceException("site xml file error -> " + file.getAbsolutePath());
            }
            
            site.queue = new TaskQueue();
            site.queue.init();
            site.counter = new Counter();
            
            if ("1".equals(site.getEnable())) {
                sites.add(site);
            }
        }
        logger.info("[INIT CONF SUCCESS][load xml config files success!]");
    }

    @Override
    public List<Site> getCompoentDescriptor() {
        return sites;
    }

}
