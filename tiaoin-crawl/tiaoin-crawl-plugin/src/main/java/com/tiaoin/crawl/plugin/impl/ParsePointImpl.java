package com.tiaoin.crawl.plugin.impl;

import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.fetcher.Page;
import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.ParsePoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;
import com.tiaoin.crawl.core.xml.Target;
import com.tiaoin.crawl.plugin.util.ModelParser;

public class ParsePointImpl implements ParsePoint {

    private Task           task;
    private SpiderListener listener;
    private Target         target;
    private Page           page;

    public void init(Site site, SpiderListener listener) {
        this.listener = listener;
    }

    public void destroy() {
    }

    public void context(Task task, Target target, Page page) throws Exception {
        this.task = task;
        this.target = target;
        this.page = page;
    }

    public List<Map<String, Object>> parse(List<Map<String, Object>> models) throws Exception {
        return parseTargetModelByXpathAndRegex();
    }

    private List<Map<String, Object>> parseTargetModelByXpathAndRegex() throws Exception {
        ModelParser parser = new ModelParser(task, target, listener);
        return parser.parse(page);
    }
}
