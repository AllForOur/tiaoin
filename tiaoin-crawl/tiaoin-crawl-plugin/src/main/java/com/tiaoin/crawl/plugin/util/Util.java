package com.tiaoin.crawl.plugin.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.url.UrlRuleChecker;
import com.tiaoin.crawl.core.xml.Target;

public class Util {

    public static Target isTargetUrl(Task task) throws Exception {
        for (Target target : task.site.getTargets().getTarget()) {
            if (UrlRuleChecker.check(task.url, target.getUrls().getRule()))
                return target;
        }

        return null;
    }

    public static Collection<String> findAllLinkHref(String html, String hostUrl) throws Exception {
        Collection<String> urls = new ArrayList<String>();

        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(html);
        Object[] ns = node.evaluateXPath("//a[@href]");
        for (Object object : ns) {
            TagNode node2 = (TagNode) object;
            String href = node2.getAttributeByName("href");
            if (href == null || href.trim().length() == 0)
                continue;

            if (!href.startsWith("https://") && !href.startsWith("http://")) {
                href = new StringBuilder("http://").append(new URL(hostUrl).getHost()).append("/")
                    .append(href).toString();
            }

            href = URLCanonicalizer.getCanonicalURL(href);
            if (href == null)
                continue;
            if (href.startsWith("mailto:"))
                continue;

            urls.add(href);
        }

        return urls;
    }
}
