package com.tiaoin.crawl.core.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * TODO
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-9 下午12:23:53
 */
@XStreamAlias("parsers")
public class Parsers {

    @XStreamImplicit(itemFieldName = "parser")
    List<Parser> parser = new ArrayList<Parser>();

    public List<Parser> getParser() {
        return this.parser;
    }

    public void setParser(List<Parser> parser) {
        this.parser = parser;
    }

}
