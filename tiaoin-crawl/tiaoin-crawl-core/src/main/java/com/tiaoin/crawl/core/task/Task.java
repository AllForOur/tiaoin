package com.tiaoin.crawl.core.task;

import com.tiaoin.crawl.core.xml.Site;

public class Task {

    public Task(String url, Site site, int sort) {
        super();
        this.url = url;
        this.site = site;
        this.sort = sort;
    }

    public Site   site;
    public int    sort = 10;
    public String url;

    public String toString() {
        return "Task [site=" + site.getName() + ", sort=" + sort + ", url=" + url + "]";
    }
}
