package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-7 下午08:10:09
 */
@XStreamAlias("headers")
public class Headers {

    @XStreamImplicit(itemFieldName = "header")
    private List<Header> header = new ArrayList<Header>();

    public List<Header> getHeader() {
        return this.header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

}
