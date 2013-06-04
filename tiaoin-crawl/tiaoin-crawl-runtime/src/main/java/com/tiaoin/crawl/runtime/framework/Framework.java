package com.tiaoin.crawl.runtime.framework;

import javax.annotation.Resource;

import com.tiaoin.crawl.core.component.DefaultComponent;

/**
 * 系统框架，启动加载系统所需要的资源
 * 
 * @author sky.yang
 * @version $Id: Framework.java, v 1.0 2013-6-1 上午2:14:02 sky.yang Exp $
 */
public class Framework  {
    @Resource
    private DefaultComponent defaultComponent;
    

    /**
     * 系统框架初始化方法
     */
    public void init() {
        defaultComponent.init();
    }
}
