package com.tiaoin.crawl.core.plugin;

import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.task.Task;

public interface EndPoint extends Point {

    void context(Task task, List<Map<String, Object>> models) throws Exception;

    List<Map<String, Object>> complete(List<Map<String, Object>> models) throws Exception;

}
