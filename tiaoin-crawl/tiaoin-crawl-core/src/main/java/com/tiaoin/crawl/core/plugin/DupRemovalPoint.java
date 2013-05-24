package com.tiaoin.crawl.core.plugin;

import java.util.Collection;

import com.tiaoin.crawl.core.task.Task;

public interface DupRemovalPoint extends Point {

    void context(Task task, Collection<String> newUrls);

    Collection<Task> removeDuplicateTask(Collection<Task> tasks);
}
