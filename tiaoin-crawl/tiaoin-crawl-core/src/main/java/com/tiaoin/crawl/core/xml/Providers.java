package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author weiwei
 *
 */
@XStreamAlias("providers")
public class Providers {

    @XStreamImplicit(itemFieldName = "provider")
    private List<Provider> provider = new ArrayList<Provider>();

    public List<Provider> getProvider() {
        return provider;
    }

    public void setProvider(List<Provider> provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Providers [provider=" + provider + "]";
    }

}
