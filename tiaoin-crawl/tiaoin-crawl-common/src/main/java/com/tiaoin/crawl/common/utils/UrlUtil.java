package com.tiaoin.crawl.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtil {

    public static String getClassesPath() {
        String path = "";
        try {
            path = URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("")
                .getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return path;
    }
}
