package com.tiaoin.crawl.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("plugin")
public class Plugin {

    @XStreamAsAttribute
    @XStreamAlias("enable")
    private String     enable  = "1";

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String     name    = "spider_plugin";

    @XStreamAsAttribute
    @XStreamAlias("version")
    private String     version = "0.0.1";

    @XStreamAsAttribute
    @XStreamAlias("desc")
    private String     desc    = "这是官方实现的默认插件，实现了所有扩展点。";

    @XStreamAlias("providers")
    private Providers  providers;

    @XStreamAlias("extensions")
    private Extensions extensions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public Extensions getExtensions() {
        return extensions;
    }

    public void setExtensions(Extensions extensions) {
        this.extensions = extensions;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }

    @Override
    public String toString() {
        return "Plugin [name=" + name + ", version=" + version + ", desc=" + desc + ", providers="
               + providers + "]";
    }

}
