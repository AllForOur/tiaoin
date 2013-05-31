package com.tiaoin.crawl.core;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.BeginPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class Hello111 implements BeginPoint {
    public void init(Site site) {
    }

    public void destroy() {

    }

    public Task confirmTask(Task task) {
        System.out.println("Hello, 我是 hello111 我拿到的 task 是 -> " + task);
        task.sort = -5;
        System.out.println("我将要返回 task ->" + task);
        return task;
    }

}
