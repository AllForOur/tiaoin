package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-7 下午08:10:09
 */
@XStreamAlias("header")
public class Header {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;

    @XStreamAsAttribute
    @XStreamAlias("value")
    private String value;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
