package com.tiaoin.crawl.plugin.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.TaskPushPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.utils.StringUtil;
import com.tiaoin.crawl.core.xml.Site;

public class TaskPushPointImpl implements TaskPushPoint {

    public void init(Site site, SpiderListener listener) {
    }

    public void destroy() {
    }

    public Collection<Task> pushTask(Collection<Task> validTasks) throws Exception {
        Collection<Task> newTasks = new ArrayList<Task>();
        for (Task task : validTasks) {
            try {
                //如果不是同一个Host
                if (!StringUtil.isEqual(task.site.getUrl(), task.url))
                    continue;

                boolean isOk = task.site.queue.pushTask(task);
                if (isOk)
                    newTasks.add(task);
            } catch (Exception e) {
                continue;
            }
        }

        return newTasks;
    }

}
