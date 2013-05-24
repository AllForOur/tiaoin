package com.tiaoin.crawl.core.conf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p>
 *  Configration的工厂方法
 * </p>
 * 
 * <p>
 * 对应于系统的 配置文件加载顺序为：
 * <li> 1. ${system.home}/config/spiderman.properties </li>
 * <li> 2. ${user.home}/spiderman.properties</li>
 * <li> 3. ${classpath}/spiderman.properties</li>
 * </p>
 * <p>
 * 如果找到对以的配置文件，将停止查找
 * 不支持set方法，系统配置，在运行时是不能改变的
 * 
 * TODO 加载资源方式需要修改
 * TODO 将在第二版中考虑配置的加载问题，有可能会使用Apache的Commons Configration
 * @author sky.yang
 *
 */
public class ConfigrationFactory {

    private static ConfigrationImpl configImpl;

    private static String           DEFAULT_HOST_NAME = "app";

    private static final char       COLON             = ':';

    //    private static Logger           logger            = DynamicLogManager.getLog(BOOT_LOG);

    // 临时的系统版本信息。
    private static final String     VERSION           = "1.0";

    /**
     * 取得Configration对象
     * 
     * <p>
     * 不需要加同步处理，Configration一般都是在系统启动时进行加载，启动的时候为单线程加载
     * </p>
     * @return
     */
    public static Configration getConfigration() {
        if (configImpl == null) {
            configImpl = new ConfigrationImpl();

            //从配置文件中加载配置
            loadFromConfig();
            //从系统属性中加载配置
            loadFromSystem();
            // 打印系统参数
            print();
        }
        return configImpl;
    }

    /**
     * 从配置文件中加载配置
     * 
     * <p>
     * 一般就是spiderman.properties文件
     * </p>
     */
    private static void loadFromConfig() {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

        //这个是对发布后环境的支持
        URL systemConfigUrl = currentClassLoader.getResource("spiderman.properties");

        //这个是对测试环境的支持
        //TODO 暂时先写死处理，以后会相应的做改变
        if (systemConfigUrl == null) {
            systemConfigUrl = currentClassLoader.getResource("spiderman-test.properties");
        }

        if (systemConfigUrl == null) {
            throw new RuntimeException("can not find system's config [spiderman.properties]!");
        }

        Properties systemProperties = new Properties();
        try {
            systemProperties.load(systemConfigUrl.openStream());
        } catch (IOException e) {
            throw new RuntimeException(
                "can not find system's config [spiderman.properties] details [" + e.getMessage()
                        + "]");
        }

        Set<Map.Entry<Object, Object>> entrySet = systemProperties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            // 从系统参数中取值，如果系统参数中有值，系统参数的值生效
            String sysValue = System.getProperty(key);
            if (sysValue != null && sysValue.length() > 0) {
                System.out.println("在Java -D参数中发现key[" + key + "]，将使用系统参数设置的值[" + sysValue
                                   + "] 替换原有的值 [" + value + "]");
                value = sysValue;
            }

            configImpl.setProperty(key, value);
        }

    }

    /**
     * 从系统属性中加载配置
     * 
     * <p>
     * 从系统属性中读取配置信息，不是所有的配置都是有用的，所以没有必要都做处理
     * </p>
     */
    private static void loadFromSystem() {
        // 设置系统唯一标识符
        String identify = System.getProperty(Configration.SYS_IDENTIFY);
        if (identify != null && identify.length() > 0) {
            configImpl.setProperty(Configration.SYS_IDENTIFY, identify);
        }

        // 设置本地的host名称
        String hostName = getHostName();
        configImpl.setProperty(Configration.SYS_HOST_NAME, hostName);

        // 设置本机ip
        String ip = getNetworkAddress();
        configImpl.setProperty(Configration.SYS_IP, ip);

        // 设置系统版本
        configImpl.setProperty(Configration.SYS_VERSION, VERSION);

        // 校验本机的应用名称
        String sysAppName = configImpl.getPropertyValue(Configration.SYS_APP_NAME);
        if (sysAppName == null || sysAppName.length() == 0) {
            System.out
                .println("必须在spiderman.properties或者spiderman-test.properties文件中设置app_name属性，用来标识应用的类型");
            //            throw new RuntimeException(
            //            		"必须在spiderman.properties或者spiderman-test.properties文件中设置app_name属性，用来标识应用的类型");
        }
    }

    private static void print() {
        Map<String, String> configs = configImpl.getConfig();
        Set<Map.Entry<String, String>> configSet = configs.entrySet();

        System.out.println("系统启动参数:");
        for (Iterator<Map.Entry<String, String>> iterator = configSet.iterator(); iterator
            .hasNext();) {
            Map.Entry<String, String> entry = iterator.next();

            System.out.println(" | " + entry.getKey() + "  [" + entry.getValue() + "]");
        }
    }

    /**
     * 在超过一块网卡时有点问题，因为这里每次都只是取了第一块网卡绑定的IP地址
     */
    private static String getNetworkAddress() {
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(COLON) == -1) {
                        return ip.getHostAddress();
                    } else {
                        continue;
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 取得主机名称。
     * @return
     */
    private static String getHostName() {
        // 设置本地的host名称
        String hostName = DEFAULT_HOST_NAME;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            //ignore
            hostName = DEFAULT_HOST_NAME;
        }
        return hostName;
    }

    public static void main(String[] args) {
        String hostName = DEFAULT_HOST_NAME;
        String ip = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();

            ip = getNetworkAddress();
        } catch (UnknownHostException e) {
            //ignore
            e.printStackTrace();
        }

        System.out.println("ip :" + ip);
        System.out.println("hostname :" + hostName);
    }
}
