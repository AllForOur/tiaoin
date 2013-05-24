package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 * @author weiwei
 *
 */
@XStreamAlias("provider")
public class Provider {

    @XStreamImplicit(itemFieldName = "orgnization")
    private List<Orgnization> orgnization = new ArrayList<Orgnization>();

    public List<Orgnization> getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(List<Orgnization> orgnization) {
        this.orgnization = orgnization;
    }

    @Override
    public String toString() {
        return "Provider [orgnization=" + orgnization + "]";
    }

}
