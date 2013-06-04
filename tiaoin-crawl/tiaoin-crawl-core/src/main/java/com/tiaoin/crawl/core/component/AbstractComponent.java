package com.tiaoin.crawl.core.component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractComponent implements Component, ApplicationContextAware {

    @Resource
    private ApplicationContext applicationContext;
    protected static List<Component> components;
    /**
     * 处理顺序
     */
    protected int level;
    
    public synchronized void register(Component component) {
        if(null == components) {
            components = new ArrayList<Component>();
        }
        
        components.add(component);
    }
    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    } 

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }
}
