package com.tiaoin.crawl.plugin.util;

import java.io.StringWriter;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Node;

import com.tiaoin.crawl.core.utils.StringUtil;

/**
 * TODO
 * @author weiwei l.weiwei@163.com
 * @date 2013-1-5 下午07:44:16
 */
public class ParserUtil {

    public static String xml(Object node, boolean keepHeader) {
        String xml = "";
        try {
            if (node instanceof Node) {
                xml = StringUtil.toXml((Node) node, keepHeader);
            } else if (node instanceof TagNode) {
                StringWriter sw = new StringWriter();
                //TODO 从配置文件里加载这个CleanerProperties
                CleanerProperties prop = new HtmlCleaner().getProperties();
                SimpleXmlSerializer ser = new SimpleXmlSerializer(prop);
                ser.write((TagNode) node, sw, "UTF-8");
                String html = sw.getBuffer().toString();
                if (keepHeader)
                    xml = html;
                else
                    xml = html.substring(html.indexOf("?>") + 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtil.toHTML(xml);
    }

}
