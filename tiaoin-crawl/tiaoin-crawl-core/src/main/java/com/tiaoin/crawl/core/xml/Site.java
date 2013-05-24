package com.tiaoin.crawl.core.xml;

import java.util.Collection;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.tiaoin.crawl.core.fetcher.PageFetcher;
import com.tiaoin.crawl.core.plugin.BeginPoint;
import com.tiaoin.crawl.core.plugin.DigPoint;
import com.tiaoin.crawl.core.plugin.DupRemovalPoint;
import com.tiaoin.crawl.core.plugin.EndPoint;
import com.tiaoin.crawl.core.plugin.FetchPoint;
import com.tiaoin.crawl.core.plugin.ParsePoint;
import com.tiaoin.crawl.core.plugin.PojoPoint;
import com.tiaoin.crawl.core.plugin.TargetPoint;
import com.tiaoin.crawl.core.plugin.TaskPollPoint;
import com.tiaoin.crawl.core.plugin.TaskPushPoint;
import com.tiaoin.crawl.core.plugin.TaskSortPoint;
import com.tiaoin.crawl.core.spider.Counter;
import com.tiaoin.crawl.core.task.TaskQueue;

@XStreamAlias("site")
public class Site {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String                     name;                      //网站名

    @XStreamAsAttribute
    @XStreamAlias("url")
    private String                     url;                       //网站url

    @XStreamAsAttribute
    @XStreamAlias("reqDelay")
    private String                     reqDelay  = "200";         //每个请求的延迟时间

    @XStreamAsAttribute
    @XStreamAlias("charset")
    private String                     charset;                   //网站内容字符集

    @XStreamAsAttribute
    @XStreamAlias("enable")
    private String                     enable    = "1";           //是否开启本网站的抓取

    @XStreamAsAttribute
    @XStreamAlias("schedule")
    private String                     schedule  = "1h";          //每隔多长时间重头爬起

    @XStreamAsAttribute
    @XStreamAlias("thread")
    private String                     thread    = "1";           //线程数

    @XStreamAsAttribute
    @XStreamAlias("waitQueue")
    private String                     waitQueue = "1s";          //当队列空的时候爬虫等待时间

    @XStreamAlias("headers")
    private Headers                    headers   = new Headers(); //HTTP头

    @XStreamAlias("cookies")
    private Cookies                    cookies   = new Cookies(); //HTTP Cookie

    @XStreamAlias("queueRules")
    private Urls                       queueRules;                //允许进入抓取队列的url规则

    @XStreamAlias("targets")
    private Targets                    targets;                   //抓取目标

    @XStreamAlias("plugins")
    private Plugins                    plugins;                   //插件

    //------------------------------------------
    public Boolean                     isStop    = false;         //每个网站都有属于自己的一个停止信号，用来标识该网站的状态是否停止完全
    public TaskQueue                   queue;                     //每个网站都有属于自己的一个任务队列容器
    public PageFetcher                 fetcher;                   //每个网站都有属于自己的一个抓取器
    public Counter                     counter;                   //针对本网站已完成的任务数量
    //------------------------------------------
    //--------------扩展点-----------------------
    public Collection<TaskPollPoint>   taskPollPointImpls;
    public Collection<BeginPoint>      beginPointImpls;
    public Collection<FetchPoint>      fetchPointImpls;
    public Collection<DigPoint>        digPointImpls;
    public Collection<DupRemovalPoint> dupRemovalPointImpls;
    public Collection<TaskSortPoint>   taskSortPointImpls;
    public Collection<TaskPushPoint>   taskPushPointImpls;
    public Collection<TargetPoint>     targetPointImpls;
    public Collection<ParsePoint>      parsePointImpls;
    public Collection<EndPoint>        endPointImpls;
    public Collection<PojoPoint>       pojoPointImpls;

    //-------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getReqDelay() {
        return this.reqDelay;
    }

    public void setReqDelay(String reqDelay) {
        this.reqDelay = reqDelay;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getWaitQueue() {
        return waitQueue;
    }

    public void setWaitQueue(String waitQueue) {
        this.waitQueue = waitQueue;
    }

    public Targets getTargets() {
        return targets;
    }

    public void setTargets(Targets targets) {
        this.targets = targets;
    }

    public Plugins getPlugins() {
        return plugins;
    }

    public void setPlugins(Plugins plugins) {
        this.plugins = plugins;
    }

    public Urls getQueueRules() {
        return queueRules;
    }

    public void setQueueRules(Urls queueRules) {
        this.queueRules = queueRules;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Headers getHeaders() {
        return this.headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Cookies getCookies() {
        return this.cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

}
