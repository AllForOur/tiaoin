package com.tiaoin.crawl.core.component;

import java.util.Collections;

import com.tiaoin.crawl.core.model.LevelComparator;

/**
 * 默认的组件实现器
 * 
 * @author sky.yang
 * @version $Id: DefaultComponent.java, v 1.0 2013-6-1 上午2:42:39 sky.yang Exp $
 */
public class DefaultComponent extends AbstractComponent {

    @Override
    public void init() {
        /**
         * 注册所有的组件,用于接收组件列表,同时调用每个组件的init方法
         */
        String[] componentNames = getApplicationContext().getBeanNamesForType(Component.class);
        for(String componentName : componentNames) {
            Component component = (Component)getApplicationContext().getBean(componentName);
            if(!component.getClass().getName().contains("DefaultComponent")) {
                component.register(component);
            }
            //component.init();
        }
        
        //根据组件的级别进行初始化的顺序
        Collections.sort(components, new LevelComparator());
        for(Component component : components) {
            component.init();
        }
    }

}
