package com.tiaoin.crawl.core.plugin;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.xml.Site;

public interface Point {

    public void init(Site site);

    public void destroy();

}
