package com.tiaoin.crawl.core.conf;

import java.util.Map;

/**
 * 系统配置类
 * @author sky.yang
 *
 */
public interface Configration {

    // ~~~~ 系统属性

    /** 系统唯一标识符  */
    public static final String SYS_IDENTIFY   = "identify";

    /** 本地主机名称 */
    public static final String SYS_HOST_NAME  = "sys_host_name";
    /** 本机ip */
    public static final String SYS_IP         = "sys_ip";
    /** 应用名称 */
    public static final String SYS_APP_NAME   = "app_name";
    /** 运行模式 */
    public static final String SYS_RUN_MODE   = "run_mode";

    public static final String SYS_VERSION    = "sys_version";

    public static final String SYS_LOG_CONFIG = "sys_log_config";

    // ~~~~接口方法，为只读

    /**
     * 取得系统中的配置信息
     * 
     * <p>
     * 这里返回的Map的是安全的，可以随便修改
     * </p>
     * @return
     */
    public Map<String, String> getConfig();

    /**
     * 根据key取得系统中的配置信息
     * @param key
     * @return
     */
    public String getPropertyValue(String key);

    /**
     * 取得应用名称。
     * 
     * @return
     */
    public String getSysAppName();

    /**
     * 取得系统的ip地址。
     * 
     * @return
     */
    public String getSysIp();

    /**
     * 取得应用的运行模式，包括测试和生产模式。
     * 
     * @return
     */
    public String getSysRunMode();

    /**
     * 取得应用的主机名称。
     * 
     * @return
     */
    public String getSysHostName();

    /**
     * 取得Sofa系统的当前版本。
     * @return
     */
    public String getSysVersion();

    /**
     * 获系统日志的个性化配置
     * @return
     */
    public String getSysLogConfig();

    /**
     * 根据key取得系统中的配置信息
     * 如果取到的值为空用返回默认值
     * 
     * @param key
     * @return
     */
    public String getPropertyValue(String key, String defaultValue);

}