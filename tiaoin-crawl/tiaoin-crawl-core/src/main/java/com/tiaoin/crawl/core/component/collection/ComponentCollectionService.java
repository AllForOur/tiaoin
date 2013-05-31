package com.tiaoin.crawl.core.component.collection;

import java.util.List;

import com.tiaoin.crawl.core.component.Component;

/**
 * 组件业务收集服务接口
 * 
 * @author sky.yang
 * @version $Id: ComponentCollentionService.java, v 1.0 2013-5-29 上午10:43:09 sky.yang Exp $
 */
public interface ComponentCollectionService {

    /**
     * 初始化
     */
    public void init();
    
    /**
     * 注册业务服务组件
     */
    public void register(Component component);
    
    /**
     * 获取组件列表
     * 
     * @return
     */
    public List<Component> getComponent();
}
