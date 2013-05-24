package com.tiaoin.crawl.core.plugin;

import com.tiaoin.crawl.core.task.Task;

public interface TaskPollPoint extends Point {

    Task pollTask() throws Exception;

}
