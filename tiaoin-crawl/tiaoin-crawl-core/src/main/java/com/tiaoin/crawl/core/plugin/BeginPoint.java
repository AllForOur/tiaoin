package com.tiaoin.crawl.core.plugin;

import com.tiaoin.crawl.core.task.Task;

/**
 * 
 * 扩展点：爬虫开始时
 * @author weiwei
 *
 */
public interface BeginPoint extends Point {

    Task confirmTask(Task task) throws Exception;

}
