package com.tiaoin.crawl.core;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.BeginPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class Hello222 implements BeginPoint {

    public void init(Site site) {
    }

    public void destroy() {

    }

    public Task confirmTask(Task task) {
        System.out.println("Hello, 我是 hello222 我拿到的task是 -> " + task);
        return task;
    }

}
