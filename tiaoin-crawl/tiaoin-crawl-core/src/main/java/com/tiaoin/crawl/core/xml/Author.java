package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author weiwei
 *
 */
@XStreamAlias("author")
public class Author {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name    = "weiwei";

    @XStreamAsAttribute
    @XStreamAlias("website")
    private String website = "http://laiweiweihi.iteye.com";

    @XStreamAsAttribute
    @XStreamAlias("email")
    private String email   = "l.weiwei@163.com";

    @XStreamAsAttribute
    @XStreamAlias("weibo")
    private String weibo   = "http://weibo.com/weiweimiss";

    @XStreamAsAttribute
    @XStreamAlias("desc")
    private String desc    = "一个喜欢自由、音乐、绘画的IT老男孩";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    @Override
    public String toString() {
        return "Author [name=" + name + ", website=" + website + ", email=" + email + ", weibo="
               + weibo + ", desc=" + desc + "]";
    }

}
