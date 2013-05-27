package com.tiaoin.crawl.core.listener;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tiaoin.crawl.core.fetcher.FetchResult;
import com.tiaoin.crawl.core.fetcher.Page;
import com.tiaoin.crawl.core.task.Task;

/**
 * 爬虫监听适配器
 * 
 * @author sky.yang
 * @version $Id: SpiderListenerAdaptor.java, v 1.0 2013-5-24 下午6:38:19 sky.yang Exp $
 */
@Service
public class SpiderListenerAdaptor implements SpiderListener {
    public void onFetch(Thread thread, Task task, FetchResult result) {
    }

    public void onNewUrls(Thread thread, Task task, Collection<String> newUrls) {
    }

    public void onDupRemoval(Thread currentThread, Task task, Collection<Task> validTasks) {
    }

    public void onNewTasks(Thread thread, Task task, Collection<Task> newTasks) {
    }

    public void onTargetPage(Thread thread, Task task, Page page) {
    }

    public void onParse(Thread thread, Task task, List<Map<String, Object>> models) {
    }

    public void onPojo(Thread thread, Task task, List<Object> pojos) {
    }

    public void onInfo(Thread thread, Task task, String info) {
    }

    public void onError(Thread thread, Task task, String err, Exception e) {
    }
}
