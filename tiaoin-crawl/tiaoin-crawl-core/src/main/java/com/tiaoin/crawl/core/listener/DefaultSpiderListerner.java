package com.tiaoin.crawl.core.listener;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.fetcher.FetchResult;
import com.tiaoin.crawl.core.fetcher.Page;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.utils.DateUtil;
import com.tiaoin.crawl.core.utils.FileUtil;
import com.tiaoin.crawl.core.utils.StringUtil;

public class DefaultSpiderListerner implements SpiderListener {

    private final Object mutex = new Object();

    @Override
    public void onNewUrls(Thread thread, Task task, Collection<String> newUrls) {
    }

    @Override
    public void onFetch(Thread thread, Task task, FetchResult result) {
    }

    @Override
    public void onDupRemoval(Thread currentThread, Task task, Collection<Task> validTasks) {
    }

    @Override
    public void onNewTasks(Thread thread, Task task, Collection<Task> newTasks) {
    }

    @Override
    public void onTargetPage(Thread thread, Task task, Page page) {
    }

    @Override
    public void onParse(Thread thread, Task task, List<Map<String, Object>> models) {
        synchronized (mutex) {
            String content = StringUtil.toJson(models.get(0));

            try {
                File dir = new File("d:/jsons/" + task.site.getName());
                if (!dir.exists())
                    dir.mkdirs();
                File file = new File(dir + "/count_" + task.site.counter.getCount() + "_"
                                     + DateUtil.getNowTime("yyyy_MM_dd_HH_mm_ss") + ".json");
                FileUtil.writeFile(file, content);
                System.out.print("[SPIDERMAN] " + DateUtil.getNowTime("HH:mm:ss") + " [INFO] ~ ");
                System.out.println(file.getAbsolutePath() + " create finished...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPojo(Thread thread, Task task, List<Object> pojos) {
    }

    @Override
    public void onInfo(Thread thread, Task task, String info) {
        //System.out.print("[SPIDERMAN] "+CommonUtil.getNowTime("HH:mm:ss")+" [INFO] ~ ");
        System.out.println(info);
    }

    @Override
    public void onError(Thread thread, Task task, String err, Exception e) {
        e.printStackTrace();
    }

}
