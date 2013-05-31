package com.tiaoin.crawl.core.component.loader;

/**
 * 组件加载器
 * 负责加载内部的业务组件
 * 
 * @author sky.yang
 * @version $Id: BusinessLoader.java, v 1.0 2013-5-28 下午4:56:04 sky.yang Exp $
 */
public interface ComponentLoader<T> {

    /**
     * 加载组件
     */
    public void loader();
    
    /**
     * 获取组件描述器
     * 
     * @return
     */
    public T getCompoentDescriptor();
}
