package com.tiaoin.crawl.core.plugin;

import java.util.Collection;

import com.tiaoin.crawl.core.fetcher.FetchResult;
import com.tiaoin.crawl.core.task.Task;

public interface DigPoint extends Point {

    void context(FetchResult result, Task task) throws Exception;

    Collection<String> digNewUrls(Collection<String> urls) throws Exception;

}
