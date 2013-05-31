package com.tiaoin.crawl.plugin.impl;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.TargetPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;
import com.tiaoin.crawl.core.xml.Target;
import com.tiaoin.crawl.plugin.util.Util;

public class TargetPointImpl implements TargetPoint {

    private Task task = null;

    public void init(Site site) {
    }

    public void destroy() {
    }

    public void context(Task task) throws Exception {
        this.task = task;
    }

    public Target confirmTarget(Target target) throws Exception {
        return Util.isTargetUrl(task);
    }

}
