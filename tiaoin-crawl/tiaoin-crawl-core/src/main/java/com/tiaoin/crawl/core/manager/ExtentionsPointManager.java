package com.tiaoin.crawl.core.manager;

import java.util.Collection;
import java.util.List;

import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.BeginPoint;
import com.tiaoin.crawl.core.plugin.DigPoint;
import com.tiaoin.crawl.core.plugin.DupRemovalPoint;
import com.tiaoin.crawl.core.plugin.EndPoint;
import com.tiaoin.crawl.core.plugin.ExtensionPoint;
import com.tiaoin.crawl.core.plugin.ExtensionPoints;
import com.tiaoin.crawl.core.plugin.FetchPoint;
import com.tiaoin.crawl.core.plugin.ParsePoint;
import com.tiaoin.crawl.core.plugin.Point;
import com.tiaoin.crawl.core.plugin.PojoPoint;
import com.tiaoin.crawl.core.plugin.TargetPoint;
import com.tiaoin.crawl.core.plugin.TaskPollPoint;
import com.tiaoin.crawl.core.plugin.TaskPushPoint;
import com.tiaoin.crawl.core.plugin.TaskSortPoint;
import com.tiaoin.crawl.core.spider.Spiderman;
import com.tiaoin.crawl.core.xml.Site;

public class ExtentionsPointManager extends AbstractManager {
    
    @Override
    public void init() {
    }

    @Override
    public void validate() {
    }

    public void loadExtendsPoint(Site site, PluginManager pluginManager) {
        // 加载TaskPoll扩展点实现类
        ExtensionPoint<TaskPollPoint> taskPollPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.task_poll);
        if (taskPollPoint != null) {
            site.taskPollPointImpls = taskPollPoint.getExtensions();
            firstInitPoint(site.taskPollPointImpls, site);
        }

        // 加载Begin扩展点实现类
        ExtensionPoint<BeginPoint> beginPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.begin);
        if (beginPoint != null) {
            site.beginPointImpls = beginPoint.getExtensions();
            firstInitPoint(site.beginPointImpls, site);
        }

        // 加载Fetch扩展点实现类
        ExtensionPoint<FetchPoint> fetchPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.fetch);
        if (fetchPoint != null) {
            site.fetchPointImpls = fetchPoint.getExtensions();
            firstInitPoint(site.fetchPointImpls, site);
        }

        // 加载Dig扩展点实现类
        ExtensionPoint<DigPoint> digPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.dig);
        if (digPoint != null) {
            site.digPointImpls = digPoint.getExtensions();
            firstInitPoint(site.digPointImpls, site);
        }

        // 加载DupRemoval扩展点实现类
        ExtensionPoint<DupRemovalPoint> dupRemovalPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.dup_removal);
        if (dupRemovalPoint != null) {
            site.dupRemovalPointImpls = dupRemovalPoint.getExtensions();
            firstInitPoint(site.dupRemovalPointImpls, site);
        }
        // 加载TaskSort扩展点实现类
        ExtensionPoint<TaskSortPoint> taskSortPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.task_sort);
        if (taskSortPoint != null) {
            site.taskSortPointImpls = taskSortPoint.getExtensions();
            firstInitPoint(site.taskSortPointImpls, site);
        }

        // 加载TaskPush扩展点实现类
        ExtensionPoint<TaskPushPoint> taskPushPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.task_push);
        if (taskPushPoint != null) {
            site.taskPushPointImpls = taskPushPoint.getExtensions();
            firstInitPoint(site.taskPushPointImpls, site);
        }

        // 加载Target扩展点实现类
        ExtensionPoint<TargetPoint> targetPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.target);
        if (targetPoint != null) {
            site.targetPointImpls = targetPoint.getExtensions();
            firstInitPoint(site.targetPointImpls, site);
        }

        // 加载Parse扩展点实现类
        ExtensionPoint<ParsePoint> parsePoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.parse);
        if (parsePoint != null) {
            site.parsePointImpls = parsePoint.getExtensions();
            firstInitPoint(site.parsePointImpls, site);
        }

        // 加载Pojo扩展点实现类
        ExtensionPoint<PojoPoint> pojoPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.pojo);
        if (pojoPoint != null) {
            site.pojoPointImpls = pojoPoint.getExtensions();
            firstInitPoint(site.pojoPointImpls, site);
        }

        // 加载End扩展点实现类
        ExtensionPoint<EndPoint> endPoint = pluginManager
            .getExtensionPoint(site.getName(),ExtensionPoints.end);
        if (endPoint != null) {
            site.endPointImpls = endPoint.getExtensions();
            firstInitPoint(site.endPointImpls, site);
        }
    }
    
    private void firstInitPoint(Collection<? extends Point> points, Site site) {
        for (Point point : points) {
            point.init(site);
        }
    }
}
