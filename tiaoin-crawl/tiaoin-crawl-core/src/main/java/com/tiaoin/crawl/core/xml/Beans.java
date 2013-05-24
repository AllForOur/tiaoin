package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.tiaoin.crawl.core.model.BasicBean;

@XStreamAlias("beans")
public class Beans extends BasicBean {

    @XStreamAlias("site")
    private Site site;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
