package com.tiaoin.crawl.plugin.impl;

import java.util.Collection;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.TaskSortPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;
import com.tiaoin.crawl.core.xml.Target;
import com.tiaoin.crawl.plugin.util.Util;

public class TaskSortPointImpl implements TaskSortPoint {

    public void init(Site site) {
    }

    public void destroy() {
    }

    public Collection<Task> sortTasks(Collection<Task> tasks) throws Exception {
        for (Task task : tasks) {
            // 如果url不符合需求，排序调回0，否则默�?0，Queue按排序从大到小取
            Target tgt = Util.isTargetUrl(task);
            if (tgt == null)
                task.sort = 0;
        }

        return tasks;
    }

}
