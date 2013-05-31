package com.tiaoin.crawl.plugin.impl;

import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.EndPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;

public class EndPointImpl implements EndPoint {

    public void init(Site site) {

    }

    public void destroy() {
    }

    public void context(Task task, List<Map<String, Object>> models) throws Exception {

    }

    public List<Map<String, Object>> complete(List<Map<String, Object>> dataMap) throws Exception {

        return dataMap;
    }

}
