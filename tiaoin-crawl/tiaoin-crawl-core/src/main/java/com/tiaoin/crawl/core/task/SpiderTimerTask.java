package com.tiaoin.crawl.core.task;

import java.util.TimerTask;

import javax.annotation.Resource;

import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.xml.Site;

public class SpiderTimerTask extends TimerTask {
    private Spiderman spiderman;
    
    public SpiderTimerTask(Spiderman spiderman) {
        this.spiderman = spiderman;
    }

    @Override
    public void run() {
        // 限制schedule的次数
        if(spiderman.outOfSchedule()) {
            cancel();
        } else {
            spiderman.startSchedule();
        }
    }

}
