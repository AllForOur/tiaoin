package com.tiaoin.crawl.core.schedule;

import java.util.Date;
import java.util.Timer;

import com.tiaoin.crawl.common.utils.StringUtil;
import com.tiaoin.crawl.core.startup.Startup;
import com.tiaoin.crawl.core.task.SpiderTimerTask;

/**
 * 爬虫调度器
 * 
 * @author sky.yang
 * @version $Id: Scheduler.java, v 1.0 2013-6-4 上午10:06:55 sky.yang Exp $
 */
public class Scheduler {
    //最大调度次数
    private int     maxScheduleTimes = 0;
    //已调度的次数
    private int     scheduleTimes    = 0;
    //调度时间
    private String  scheduleTime     = "1h";
    //调度延迟时间
    private String  scheduleDelay    = "1m";
    //时间调度器
    private Timer   timer            = new Timer();

    private Startup startup;

    public void schedule(Startup startup) {
        this.startup = startup;
        timer.schedule(new SpiderTimerTask(this), new Date(), (StringUtil.toSeconds(scheduleTime)
            .intValue() + StringUtil.toSeconds(scheduleDelay).intValue()) * 1000);

    }

    /**
     * 取消调度
     */
    public void cancelSchedule() {
        timer.cancel();
        timer = new Timer();
        startup.setSchedule(false);
    }

    /**
     * 判断是否超过了调度次数
     * 
     * @return
     */
    public boolean outOfSchedule() {
        if (maxScheduleTimes > 0 && scheduleTimes >= maxScheduleTimes) {
            return true;
        }
        return false;
    }

    /**
     * 开始调度
     */
    public void startSchedule() {
        //先阻塞,判断之前所有的网站是否都已经停止完全
        startup.canSchedule();
        // 只有所有的网站资源都已被释放[特殊情况timeout]完全才重启Spiderman
        scheduleTimes++;
        String strTimes = scheduleTimes + "";
        if (maxScheduleTimes > 0) {
            strTimes += "/" + maxScheduleTimes;
        }
        startup._startup();
        startup.keepStrict(scheduleTime);
    }
}
