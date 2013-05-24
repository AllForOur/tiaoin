package com.tiaoin.crawl.runtime;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.listener.SpiderListenerAdaptor;
import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.utils.FileUtil;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        final Object mutex = new Object();
        
        SpiderListener listener = new SpiderListenerAdaptor(){
            public void onInfo(Thread thread, Task task, String info) {
                System.out.print("[SPIDERMAN] "+System.currentTimeMillis()+" [INFO] ~ ");
                System.out.println(info);
            }
            public void onError(Thread thread, Task task, String err, Exception e) {
                e.printStackTrace();
            }
            
            public void onParse(Thread thread, Task task, List<Map<String, Object>> models) {
//              System.out.print("[SPIDERMAN] "+CommonUtil.getNowTime("HH:mm:ss")+" [INFO] ~ ");
//              System.out.println(CommonUtil.toJson(models.get(0)));
                synchronized (mutex) {
                    String content = models.get(0).toString();
                    
                    try {
                        File dir = new File("d:/jsons/"+task.site.getName());
                        if (!dir.exists())
                            dir.mkdirs();
                        File file = new File(dir+"/count_"+task.site.counter.getCount()+"_"+System.currentTimeMillis()+".json");
                        FileUtil.writeFile(file, content);
                        System.out.print("[SPIDERMAN] "+System.currentTimeMillis()+" [INFO] ~ ");
                        System.out.println(file.getAbsolutePath() + " create finished...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        Spiderman.me()
        .init(listener)//初始化
        .startup()// 启动
        .keep("30s");// 存活时间，过了存活时间后马上关闭
    }
}
