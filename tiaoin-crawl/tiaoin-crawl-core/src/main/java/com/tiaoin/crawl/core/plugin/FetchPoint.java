package com.tiaoin.crawl.core.plugin;

import com.tiaoin.crawl.core.fetcher.FetchResult;
import com.tiaoin.crawl.core.task.Task;

public interface FetchPoint extends Point {

    void context(Task task) throws Exception;

    FetchResult fetch(FetchResult result) throws Exception;

}
