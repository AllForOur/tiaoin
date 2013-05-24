package com.tiaoin.crawl.server.action;

import com.opensymphony.xwork2.ActionSupport;

public class WebsiteManageAction extends ActionSupport{
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
}
