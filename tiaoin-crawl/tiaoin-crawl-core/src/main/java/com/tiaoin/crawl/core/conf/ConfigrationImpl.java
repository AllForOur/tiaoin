package com.tiaoin.crawl.core.conf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统的配置类
 * @author sky.yang
 *
 */
public class ConfigrationImpl implements Configration {

    // ~~~~ 常量

    /** key 常量 */
    public static final String  LOG4J_PATH   = "sys_log4j_path";

    // ~~~~ 内部变量

    private Map<String, String> sysConfigMap = new LinkedHashMap<String, String>();

    // ~~~~  构造函数

    protected ConfigrationImpl() {
    }

    public Map<String, String> getConfig() {
        //对map进行clone，保证map的安全性
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(this.sysConfigMap);
        return map;
    }

    /**
     * 设置配置信息
     * 
     * @param map
     */
    public void setConfig(Map<String, String> map) {
        this.sysConfigMap.putAll(map);
    }

    public String getPropertyValue(String key) {
        return this.sysConfigMap.get(key);
    }

    /**
     * 设置配置属性
     * 
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        this.sysConfigMap.put(key, value);
    }

    public String getPropertyValue(String key, String defaultValue) {
        return this.sysConfigMap.get(key) == null ? defaultValue : this.sysConfigMap.get(key);
    }

    public String getSysAppName() {
        return getPropertyValue(SYS_APP_NAME);
    }

    public String getSysIp() {
        return getPropertyValue(SYS_IP);
    }

    public String getSysRunMode() {
        return getPropertyValue(SYS_RUN_MODE);
    }

    public String getSysHostName() {
        return getPropertyValue(SYS_HOST_NAME);
    }

    public String getSysVersion() {
        return getPropertyValue(SYS_VERSION);
    }

    public String getSysLogConfig() {
        return getPropertyValue(SYS_LOG_CONFIG);
    }

}
