package com.tiaoin.crawl.plugin.impl;

import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.plugin.BeginPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class BeginPointImpl implements BeginPoint {

    public Task confirmTask(Task task) throws Exception {
        if (!StringUtil.isEqual(task.site.getUrl(), task.url))
            return null;

        return task;
    }

    public void init(Site site) {

    }

    public void destroy() {
    }

}
