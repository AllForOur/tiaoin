package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-7 下午08:10:09
 */
@XStreamAlias("cookie")
public class Cookie {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;

    @XStreamAsAttribute
    @XStreamAlias("value")
    private String value;

    @XStreamAsAttribute
    @XStreamAlias("host")
    private String host;

    @XStreamAsAttribute
    @XStreamAlias("path")
    private String path;

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

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
