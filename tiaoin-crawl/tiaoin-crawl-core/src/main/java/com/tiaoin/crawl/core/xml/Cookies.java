package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-7 下午08:10:09
 */
@XStreamAlias("cookies")
public class Cookies {

    @XStreamImplicit(itemFieldName = "cookie")
    private List<Cookie> cookie = new ArrayList<Cookie>();

    public List<Cookie> getCookie() {
        return this.cookie;
    }

    public void setCookie(List<Cookie> cookie) {
        this.cookie = cookie;
    }

}
