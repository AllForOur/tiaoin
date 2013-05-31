package com.tiaoin.crawl.plugin.impl;

import org.springframework.stereotype.Service;

import com.tiaoin.crawl.core.plugin.TaskPollPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

@Service
public class TaskPollPointImpl implements TaskPollPoint {

    private Site site = null;

    public void init(Site site) {
        this.site = site;
    }

    public void destroy() {
    }

    public Task pollTask() throws Exception {
        return site.queue.pollTask();
    }
}
