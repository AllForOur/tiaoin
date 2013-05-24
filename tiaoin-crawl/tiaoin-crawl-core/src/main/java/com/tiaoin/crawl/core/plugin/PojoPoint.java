package com.tiaoin.crawl.core.plugin;

import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.task.Task;

/**
 * TODO
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-2 下午07:01:00
 */
public interface PojoPoint extends Point {

    void context(Task task, Class<?> mappingClass, List<Map<String, Object>> models);

    List<Object> mapping(List<Object> pojo);

}
