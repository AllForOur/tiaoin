package com.tiaoin.crawl.plugin.impl;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.TaskPollPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class TaskPollPointImpl implements TaskPollPoint {

    private Site site = null;

    public void init(Site site, SpiderListener listener) {
        this.site = site;
    }

    public void destroy() {
    }

    public Task pollTask() throws Exception {
        return site.queue.pollTask();
    }
}
