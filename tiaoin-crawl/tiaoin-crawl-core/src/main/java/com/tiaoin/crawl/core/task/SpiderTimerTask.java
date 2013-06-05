package com.tiaoin.crawl.core.task;

import java.util.TimerTask;


import com.tiaoin.crawl.core.schedule.Scheduler;

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
