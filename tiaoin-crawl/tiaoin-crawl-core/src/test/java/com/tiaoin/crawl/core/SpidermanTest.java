package com.tiaoin.crawl.core;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tiaoin.crawl.common.utils.FileUtil;
import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.listener.SpiderListenerAdaptor;
//import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.task.Task;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:**/spring-core-service.xml")
//public class SpidermanTest extends AbstractJUnit4SpringContextTests {

//    @Resource
//    private Spiderman spiderman;
//    
//    @Test
//    public void testSpiderman() {
//        final Object mutex = new Object();
//        SpiderListener listener = new SpiderListenerAdaptor(){
//            public void onInfo(Thread thread, Task task, String info) {
//                System.out.print("[SPIDERMAN] "+System.currentTimeMillis()+" [INFO] ~ ");
//                System.out.println(info);
//            }
//            public void onError(Thread thread, Task task, String err, Exception e) {
//                e.printStackTrace();
//            }
//            
//            public void onParse(Thread thread, Task task, List<Map<String, Object>> models) {
////              System.out.print("[SPIDERMAN] "+CommonUtil.getNowTime("HH:mm:ss")+" [INFO] ~ ");
////              System.out.println(CommonUtil.toJson(models.get(0)));
//                synchronized (mutex) {
//                    String content = models.get(0).toString();
//                    
//                    try {
//                        File dir = new File("d:/jsons/"+task.site.getName());
//                        if (!dir.exists())
//                            dir.mkdirs();
//                        File file = new File(dir+"/count_"+task.site.counter.getCount()+"_"+System.currentTimeMillis()+".json");
//                        FileUtil.writeFile(file, content);
//                        System.out.print("[SPIDERMAN] "+System.currentTimeMillis()+" [INFO] ~ ");
//                        System.out.println(file.getAbsolutePath() + " create finished...");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        
//        spiderman.init(listener);
//        spiderman.startup();
//        spiderman.keep("1000s");
//    }
//}
