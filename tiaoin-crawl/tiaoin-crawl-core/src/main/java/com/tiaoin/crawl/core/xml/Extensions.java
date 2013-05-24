package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("extensions")
public class Extensions {

    @XStreamImplicit(itemFieldName = "extension")
    private List<Extension> extension = new ArrayList<Extension>();

    public List<Extension> getExtension() {
        return extension;
    }

    public void setExtension(List<Extension> extension) {
        this.extension = extension;
    }

}
