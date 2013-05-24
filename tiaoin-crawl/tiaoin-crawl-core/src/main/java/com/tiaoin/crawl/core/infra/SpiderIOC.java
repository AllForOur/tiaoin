package com.tiaoin.crawl.core.infra;

public interface SpiderIOC {

    public <T> T createExtensionInstance(String name);

}
