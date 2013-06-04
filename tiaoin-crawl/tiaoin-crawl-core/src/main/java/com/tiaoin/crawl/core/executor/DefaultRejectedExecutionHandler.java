package com.tiaoin.crawl.core.executor;

import java.util.Arrays;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.tiaoin.crawl.core.spider.Spider;

public class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
    private static final Logger logger = Logger.getLogger(DefaultRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 拿到被弹出来的爬虫引用
        Spider spider = (Spider) r;
        try {
            // 将该爬虫的任务 task 放回队列
            spider.pushTask(Arrays.asList(spider.task));
            String info = "repush the task->" + spider.task + " to the Queue.";
        } catch (Exception e) {
            String err = "could not repush the task to the Queue. cause -> " + e.toString();
            logger.error(err, e);
        }
    }

}
