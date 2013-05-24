package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("field")
public class Field {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String  name;

    @XStreamAsAttribute
    @XStreamAlias("isArray")
    private String  isArray;

    @XStreamAsAttribute
    @XStreamAlias("isTrim")
    private String  isTrim;  //是否去掉前后的空格字符

    @XStreamAlias("parsers")
    private Parsers parsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsArray() {
        return isArray;
    }

    public void setIsArray(String isArray) {
        this.isArray = isArray;
    }

    public Parsers getParsers() {
        return parsers;
    }

    public void setParsers(Parsers parsers) {
        this.parsers = parsers;
    }

    public String getIsTrim() {
        return this.isTrim;
    }

    public void setIsTrim(String isTrim) {
        this.isTrim = isTrim;
    }
}
