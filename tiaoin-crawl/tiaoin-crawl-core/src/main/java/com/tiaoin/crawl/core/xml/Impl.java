package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("impl")
public class Impl {

    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;

    @XStreamAsAttribute
    @XStreamAlias("value")
    private String value;

    @XStreamAsAttribute
    @XStreamAlias("sort")
    private String sort = "0";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Impl [type=" + type + ", value=" + value + ", sort=" + sort + "]";
    }

}
