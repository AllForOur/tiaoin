package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("extension")
public class Extension {

    @XStreamAsAttribute
    @XStreamAlias("point")
    private String     point;

    @XStreamImplicit(itemFieldName = "impl")
    private List<Impl> impl = new ArrayList<Impl>();

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public List<Impl> getImpl() {
        return impl;
    }

    public void setImpl(List<Impl> impl) {
        this.impl = impl;
    }

}
