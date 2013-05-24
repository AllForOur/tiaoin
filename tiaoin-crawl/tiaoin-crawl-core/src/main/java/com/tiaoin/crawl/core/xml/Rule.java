package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("rule")
public class Rule {

    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;

    @XStreamAsAttribute
    @XStreamAlias("value")
    private String value;

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

    @Override
    public String toString() {
        return "Rule [type=" + this.type + ", value=" + this.value + "]";
    }
}
