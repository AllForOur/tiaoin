package com.tiaoin.crawl.core.spider;

import com.tiaoin.crawl.core.conf.ConfigrationFactory;
import com.tiaoin.crawl.core.utils.StringUtil;
import com.tiaoin.crawl.core.utils.UrlUtil;

public class Settings {

    public static String website_xml_folder() {
        return ConfigrationFactory.getConfigration().getPropertyValue("website.xml.folder")
            .replace("#{ClassPath}", UrlUtil.getClassesPath());
    }

    public static String website_visited_folder() {
        return ConfigrationFactory.getConfigration().getPropertyValue("website.visited.folder")
            .replace("#{ClassPath}", UrlUtil.getClassesPath());
    }

    public static int http_fetch_retry() {
        return Integer.parseInt(ConfigrationFactory.getConfigration().getPropertyValue(
            "http.fetch.retry"));
    }

    public static long http_fetch_timeout() {
        return StringUtil.toSeconds(
            ConfigrationFactory.getConfigration().getPropertyValue("http.fetch.timeout"))
            .longValue();
    }
}
