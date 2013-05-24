package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("model")
public class Model {

    @XStreamAsAttribute
    @XStreamAlias("clazz")
    private String      clazz;

    @XStreamAsAttribute
    @XStreamAlias("isArray")
    private String      isArray;

    @XStreamAsAttribute
    @XStreamAlias("xpath")
    private String      xpath;

    @XStreamImplicit(itemFieldName = "field")
    private List<Field> field = new ArrayList<Field>();

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<Field> getField() {
        return field;
    }

    public void setField(List<Field> field) {
        this.field = field;
    }

    public String getIsArray() {
        return this.isArray;
    }

    public void setIsArray(String isArray) {
        this.isArray = isArray;
    }

    public String getXpath() {
        return this.xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

}
