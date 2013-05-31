package com.tiaoin.crawl.core.manager;

import java.util.List;

import com.tiaoin.crawl.core.component.loader.ComponentLoader;
import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.xml.Site;


/**
 * 抽象层Manager类,用于处理基础信息
 * 
 * @author sky.yang
 * @version $Id: AbstractManager.java, v 1.0 2013-5-29 下午1:43:44 sky.yang Exp $
 */
public abstract class AbstractManager implements Manager {
    protected ComponentLoader<List<Site>> spiderComponentLoader;
    
    public void doInit(ComponentLoader<List<Site>> componentLoader) {
        this.spiderComponentLoader = componentLoader;
        validate();
        init();
    }

    
}
