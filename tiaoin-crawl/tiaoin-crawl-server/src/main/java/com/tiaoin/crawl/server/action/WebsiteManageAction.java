package com.tiaoin.crawl.server.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.tiaoin.crawl.core.service.StartupService;

public class WebsiteManageAction extends ActionSupport{
    @Resource
    StartupService startupService;
    /**
     * 返回列表页面
     * @return
     */
    public String websiteManageListPage(){
        return SUCCESS;
    }
    
    /**
     * 返回增加和修改页面
     * @return
     */
    public String websiteManageAddPage(){
        return SUCCESS;
    }
    
    public String startup() {
        startupService.startup();
        return SUCCESS;
    }
}
