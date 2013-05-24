package com.tiaoin.crawl.core.utils;

import java.util.Date;

/**
 * 
 * 
 * @author sky.yang
 * @version $Id: DateUtil.java, v 1.0 2013-1-21 下午4:08:46 sky.yang Exp $
 */
public class DateUtil {

    public static Long getNow() {
        return System.currentTimeMillis();
    }

    public static Long getNow(int length) {
        return getTime(length, new Date());
    }

    public static Long getTime(int length, Date date) {
        String time = String.valueOf(date.getTime()).substring(0, length);
        return Long.parseLong(time);
    }

    /**
     * 取得当前时间,给定格式
     * @return
     */
    public static String getNowTime(String format) {
        if (format == null) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        String now = new java.text.SimpleDateFormat(format).format(java.util.Calendar.getInstance()
            .getTime());
        return now;
    }

    /**
     * 取得当前时间
     * @return
     */
    public static String getNowTime() {
        return getNowTime(null);
    }

}
