package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("plugins")
public class Plugins {

    @XStreamImplicit(itemFieldName = "plugin")
    private List<Plugin> plugin = new ArrayList<Plugin>();

    public List<Plugin> getPlugin() {
        return plugin;
    }

    public void setPlugin(List<Plugin> plugin) {
        this.plugin = plugin;
    }
}
