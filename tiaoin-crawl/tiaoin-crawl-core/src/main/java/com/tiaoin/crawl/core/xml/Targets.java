package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("targets")
public class Targets {
    @XStreamImplicit(itemFieldName = "target")
    private List<Target> target = new ArrayList<Target>();

    public List<Target> getTarget() {
        return target;
    }

    public void setTarget(List<Target> target) {
        this.target = target;
    }

}
