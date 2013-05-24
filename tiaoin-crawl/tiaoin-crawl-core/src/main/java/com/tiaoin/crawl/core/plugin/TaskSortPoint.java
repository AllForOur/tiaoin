package com.tiaoin.crawl.core.plugin;

import java.util.Collection;

import com.tiaoin.crawl.core.task.Task;

public interface TaskSortPoint extends Point {

    Collection<Task> sortTasks(Collection<Task> tasks) throws Exception;

}
