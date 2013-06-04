package com.tiaoin.crawl.core.task;

import java.util.TimerTask;

import javax.annotation.Resource;

import com.tiaoin.crawl.core.schedule.Scheduler;
import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.xml.Site;

public class SpiderTimerTask extends TimerTask {
    private Scheduler scheduler;
    
    public SpiderTimerTask(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        // 限制schedule的次数
        if(scheduler.outOfSchedule()) {
            scheduler.cancelSchedule();
        } else {
            scheduler.startSchedule();
        }
    }

}
