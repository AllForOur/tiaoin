package com.tiaoin.crawl.core.plugin;

import java.util.Collection;

public interface ExtensionPoint<T> {

    public Collection<T> getExtensions();

}
