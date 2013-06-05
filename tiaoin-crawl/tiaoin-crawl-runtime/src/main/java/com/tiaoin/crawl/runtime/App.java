package com.tiaoin.crawl.runtime;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tiaoin.crawl.common.utils.FileUtil;
import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.listener.SpiderListenerAdaptor;
//import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.task.Task;

/**
 * Hello world!
 *
 */
public class App {
    private static String[] locations = {"classpath*:**/spring-core-service.xml"};
    private static ApplicationContext ctx;
    
    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext(locations);
        
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
        
//        Spiderman spiderman = (Spiderman)ctx.getBean("spiderman");
//        spiderman.init(listener);
//        spiderman.startup();
//        Spiderman.me()
//        .init(listener)//初始化
//        .startup()// 启动
//        .keep("30s");// 存活时间，过了存活时间后马上关闭
    }
}
