package com.tiaoin.crawl.core.plugin;

import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Target;

public interface TargetPoint extends Point {

    void context(Task task) throws Exception;

    Target confirmTarget(Target target) throws Exception;

}
