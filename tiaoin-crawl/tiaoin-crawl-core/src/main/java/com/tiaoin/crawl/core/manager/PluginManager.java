package com.tiaoin.crawl.core.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tiaoin.crawl.common.exception.ServiceException;
import com.tiaoin.crawl.core.plugin.ExtensionPoint;
import com.tiaoin.crawl.core.plugin.ExtensionPoints;
import com.tiaoin.crawl.core.spider.ImplComparator;
import com.tiaoin.crawl.core.xml.Extension;
import com.tiaoin.crawl.core.xml.Impl;
import com.tiaoin.crawl.core.xml.Plugin;
import com.tiaoin.crawl.core.xml.Site;

public class PluginManager extends AbstractManager implements ApplicationContextAware {
    private Logger                        logger = Logger.getLogger(PluginManager.class);
    private ApplicationContext            applicationContext;
    @Resource
    private ExtentionsPointManager        extensionsPointsManager;

    //private Map<String, Collection<Impl>> impls  = new HashMap<String, Collection<Impl>>();
    private Map<String, Map<String, Collection<Impl>>> implCollections = new HashMap<String, Map<String, Collection<Impl>>>();

    @Override
    public void init() {
        List<Site> sites = spiderComponentLoader.getCompoentDescriptor();
        for (Site site : sites) {
            Collection<Plugin> plugins = site.getPlugins().getPlugin();
            loadPluginConf(plugins, site.getName());
            extensionsPointsManager.loadExtendsPoint(site, this);
        }
        
    }

    @Override
    public void validate() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 加载配置文件。很重要 
     * @date 2013-1-2 下午07:13:49
     * @param plugins
     * @param listener
     * @throws Exception
     */
    public void loadPluginConf(Collection<Plugin> plugins, String siteName) {
        if (plugins == null || plugins.isEmpty()) {
            logger.info("[SYSTEM INFO][There is no plugin to load!]");
        }

        Map<String, Collection<Impl>> implMaps  = new HashMap<String, Collection<Impl>>();
        for (Plugin plugin : plugins) {
            if (!"1".equals(plugin.getEnable())) {
                logger.info("[SPIDER][Thread ID=" + Thread.currentThread().getId()
                            + "][skip plugin[" + plugin.getName() + "]  cause it is not enable]");
                continue;
            }

            Collection<Extension> extensions = plugin.getExtensions().getExtension();
            if (extensions == null || extensions.isEmpty()) {
                logger.info("[SPIDER][Thread ID=" + Thread.currentThread().getId() + "][plugin["
                            + plugin.getName() + "]  has no extentions to load]");
                continue;
            }

            logger.info("[SPIDER][Thread ID=" + Thread.currentThread().getId() + "][plugin info["
                        + plugin + "]]");

            for (Extension ext : extensions) {
                String point = ext.getPoint();
                if (!ExtensionPoints.contains(point)) {
                    String err = "plugin[" + plugin.getName() + "] extension-point[" + point
                                 + "] not found please confim the point value must be in "
                                 + ExtensionPoints.string();
                    logger.error("[ERROR][Thread ID=" + Thread.currentThread().getId()
                                 + "][Error=[" + err + "]]");
                    throw new ServiceException(err);
                }

                List<Impl> impls = ext.getImpl();
                if (impls == null || impls.isEmpty()) {
                    logger.info("[SPIDER][Thread ID=" + Thread.currentThread().getId()
                                + "][skip plugin[" + plugin.getName() + "]  extension-point["
                                + point + "] cause it has no impls to load]");
                    continue;
                }

                //按照排序
                Collections.sort(impls, new ImplComparator());

                //一个扩展点有多个实现类
                Collection<Impl> _impls = implMaps.get(point);
                if (_impls != null)
                    _impls.addAll(impls);
                else
                    implMaps.put(point, impls);

                logger.info("[SPIDER][Thread ID=" + Thread.currentThread().getId()
                            + "][skip plugin[" + plugin.getName() + "]  extension-point[" + point
                            + "] loading ok]");
            }
        }
        
        implCollections.put(siteName, implMaps);
    }

    /**
     * 获取扩展点的所有实现类
     * @date 2013-1-2 下午07:14:14
     * @param <T>
     * @param name
     * @return
     */
    public <T> ExtensionPoint<T> getExtensionPoint(String siteName, final String pointName) {
        if(!implCollections.containsKey(siteName)) {
            return null;
        }
        Map<String, Collection<Impl>> impls = implCollections.get(siteName);
        if (!impls.containsKey(pointName))
            return null;

        final Collection<Impl> _impls = impls.get(pointName);
        return new ExtensionPoint<T>() {
            public Collection<T> getExtensions() {
                Collection<T> result = new ArrayList<T>();
                for (Impl impl : _impls) {
                    T t = null;
                    String type = impl.getType();
                    String value = impl.getValue();
                    if ("ioc".equals(type)) {
                        //t = ioc.createExtensionInstance(value);
                        t = (T) applicationContext.getBean(value);
                    } else {
                        try {
                            Class<T> cls = (Class<T>) Class.forName(value);
                            t = cls.newInstance();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("Impl class -> " + value
                                                       + " of ExtensionPoint[" + pointName
                                                       + "] not found !", e);
                        } catch (InstantiationException e) {
                            throw new RuntimeException("Impl class -> " + value
                                                       + " of ExtensionPoint[" + pointName
                                                       + "] instaniation fail !", e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Impl class -> " + value
                                                       + " of ExtensionPoint[" + pointName
                                                       + "] illegal access !", e);
                        }
                    }

                    result.add(t);
                }

                return result;
            }
        };
    }

}
