package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * TODO
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-9 下午01:45:36
 */
@XStreamAlias("parser")
public class Parser {

    @XStreamAsAttribute
    @XStreamAlias("skipErr")
    private String skipErr;

    @XStreamAsAttribute
    @XStreamAlias("skipRgxFail")
    private String skipRgxFail;

    @XStreamAsAttribute
    @XStreamAlias("xpath")
    private String xpath;

    @XStreamAsAttribute
    @XStreamAlias("attribute")
    private String attribute;

    @XStreamAsAttribute
    @XStreamAlias("exp")
    private String exp;

    @XStreamAsAttribute
    @XStreamAlias("regex")
    private String regex;

    public String getXpath() {
        return this.xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getExp() {
        return this.exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getRegex() {
        return this.regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getSkipErr() {
        return this.skipErr;
    }

    public void setSkipErr(String skipErr) {
        this.skipErr = skipErr;
    }

    public String getSkipRgxFail() {
        return this.skipRgxFail;
    }

    public void setSkipRgxFail(String skipRgxFail) {
        this.skipRgxFail = skipRgxFail;
    }

}
