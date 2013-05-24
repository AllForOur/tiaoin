package com.tiaoin.crawl.core.plugin;

import java.util.Collection;

import com.tiaoin.crawl.core.task.Task;

public interface TaskPushPoint extends Point {

    public Collection<Task> pushTask(Collection<Task> tasks) throws Exception;

}
