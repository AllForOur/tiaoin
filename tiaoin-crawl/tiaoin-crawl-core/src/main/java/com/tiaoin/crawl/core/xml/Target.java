package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("target")
public class Target {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;

    @XStreamAlias("urls")
    private Urls   urls;

    @XStreamAlias("model")
    private Model  model;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
