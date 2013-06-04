package com.tiaoin.crawl.core.component;

import com.tiaoin.crawl.core.model.LevelSortable;

/**
 * 系统组件
 * 提供相应的组件服务
 * 
 * @author sky.yang
 * @version $Id: Component.java, v 1.0 2013-5-28 下午5:31:13 sky.yang Exp $
 */
public interface Component extends LevelSortable {
    
    /**
     * 子类初始化方法
     */
    public void init();

    /**
     * 注册组件
     */
    public void register(Component component);
}
