package com.tiaoin.crawl.core.component.event;

import org.springframework.context.ApplicationEvent;

public abstract class ComponentApplicationEvent extends ApplicationEvent {
    /**  */
    private static final long serialVersionUID = -5552724672527130668L;

    public ComponentApplicationEvent(Object source) {
        super(source);
    }

}
