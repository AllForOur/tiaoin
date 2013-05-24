package com.tiaoin.crawl.core.plugin;

import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.fetcher.Page;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Target;

public interface ParsePoint extends Point {

    void context(Task task, Target target, Page page) throws Exception;

    List<Map<String, Object>> parse(List<Map<String, Object>> models) throws Exception;

}
