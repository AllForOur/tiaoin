package com.tiaoin.crawl.plugin.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.tiaoin.crawl.common.utils.CommonUtil;
import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.plugin.TaskPushPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class TaskPushPointImpl implements TaskPushPoint {
    
    private Site mySite;

    public void init(Site site) {
        mySite = site;
    }

    public void destroy() {
    }

    public Collection<Task> pushTask(Collection<Task> validTasks) throws Exception {
        Collection<Task> newTasks = new ArrayList<Task>();
        for (Task task : validTasks) {
            try {
                //如果不是同一个Host
                if (!CommonUtil.isSameHost(task.site.getUrl(), task.url))
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
