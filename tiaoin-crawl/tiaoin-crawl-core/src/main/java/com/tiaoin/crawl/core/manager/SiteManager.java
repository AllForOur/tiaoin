package com.tiaoin.crawl.core.manager;

import java.util.List;

import com.tiaoin.crawl.common.exception.ServiceException;
import com.tiaoin.crawl.core.component.loader.ComponentLoader;
import com.tiaoin.crawl.core.xml.Site;
import com.tiaoin.crawl.core.xml.Target;


/**
 * 站点管理器
 * 
 * @author sky.yang
 * @version $Id: SiteManager.java, v 1.0 2013-5-28 下午5:45:01 sky.yang Exp $
 */
public class SiteManager extends AbstractManager  {
    
    @Override
    public void init() {
        
    }

    @Override
    public void validate() {
        List<Site> sites = (List<Site>) spiderComponentLoader.getCompoentDescriptor();
        for(Site site : sites) {
            if (site.getName() == null || site.getName().trim().length() == 0) {
                throw new ServiceException("site name required");
            }
            if (site.getUrl() == null || site.getUrl().trim().length() == 0) {
                throw new ServiceException("site url required");
            }
            if (site.getTargets() == null || site.getTargets().getTarget().isEmpty()) {
                throw new ServiceException("site target required");
            }

            List<Target> targets = site.getTargets().getTarget();
            if (targets == null || targets.isEmpty()) {
                throw new ServiceException("can not get any url target of site -> " + site.getName());
            }
        }
    }

    
}
