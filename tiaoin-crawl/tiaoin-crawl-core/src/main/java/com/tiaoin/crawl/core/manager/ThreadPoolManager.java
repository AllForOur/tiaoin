package com.tiaoin.crawl.core.manager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.tiaoin.crawl.core.executor.Executor;
import com.tiaoin.crawl.core.xml.Site;

public class ThreadPoolManager extends AbstractManager {
    private ExecutorService pool;
    @Resource
    private Executor        executor;
    //是否停止
    private boolean         isStop        = false;
    //是否现在已经已经关闭
    private boolean         isShutdownNow = false;

    @Override
    public void init() {
        List<Site> sites = spiderComponentLoader.getCompoentDescriptor();
        if (pool == null) {
            int size = sites.size();
            if (size == 0)
                throw new RuntimeException("there is no website to fetch...");
            pool = new ThreadPoolExecutor(size, size, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        }
    }

    @Override
    public void validate() {
    }

    public void execute(Site site) {
        executor.init(site, this);
        pool.execute(executor);
    }

    public void shutdown() {
        pool.shutdown();
        pool = null;
        isStop = true;
    }

    public void shutdownNow() {
        pool.shutdownNow();
        pool = null;
        isStop = true;
        isShutdownNow = true;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public boolean isStop() {
        return isStop;
    }

    public boolean isShutdownNow() {
        return isShutdownNow;
    }

}
