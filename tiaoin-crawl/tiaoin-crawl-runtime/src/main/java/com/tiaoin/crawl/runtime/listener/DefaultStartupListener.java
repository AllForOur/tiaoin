package com.tiaoin.crawl.runtime.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tiaoin.crawl.runtime.framework.Framework;



public class DefaultStartupListener implements ServletContextListener  {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext wa = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()); 
        Framework framework = wa.getBean(Framework.class);
        framework.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
