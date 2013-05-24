package com.tiaoin.crawl.plugin.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.tiaoin.crawl.core.fetcher.FetchResult;
import com.tiaoin.crawl.core.infra.DefaultLinkFinder;
import com.tiaoin.crawl.core.infra.FrameLinkFinder;
import com.tiaoin.crawl.core.infra.IframeLinkFinder;
import com.tiaoin.crawl.core.listener.SpiderListener;
import com.tiaoin.crawl.core.plugin.DigPoint;
import com.tiaoin.crawl.core.task.Task;
import com.tiaoin.crawl.core.xml.Site;
import com.tiaoin.crawl.plugin.util.DefaultLinkNormalizer;
import com.tiaoin.crawl.plugin.util.LinkNormalizer;
import com.tiaoin.crawl.plugin.util.URLCanonicalizer;
import com.tiaoin.crawl.plugin.util.Util;

public class DigPointImpl implements DigPoint {

    private FetchResult result = null;
    private Task        task   = null;

    public void init(Site site, SpiderListener listener) {

    }

    public void destroy() {
    }

    public void context(FetchResult result, Task task) throws Exception {
        this.result = result;
        this.task = task;
    }

    public Collection<String> digNewUrls(Collection<String> urls) throws Exception {
        return this.digNewUrls(result);
    }

    private Collection<String> digNewUrls(FetchResult result) throws Exception {
        if (result == null)
            return null;

        Collection<String> urls = new HashSet<String>();
        String moveUrl = result.getMovedToUrl();

        if (moveUrl != null) {
            if (!moveUrl.equals(task.url))
                urls.add(moveUrl);
        } else {
            if (result.getPage() == null)
                return null;
            String html = result.getPage().getContent();
            if (html == null)
                return null;

            urls.addAll(Util.findAllLinkHref(html, task.site.getUrl()));
            urls.addAll(new DefaultLinkFinder(html).getLinks());
            urls.addAll(new IframeLinkFinder(html).getLinks());
            urls.addAll(new FrameLinkFinder(html).getLinks());
        }

        //resolveUrl
        String hostUrl = new StringBuilder("http://").append(new URL(task.site.getUrl()).getHost())
            .append("/").toString();
        List<String> newUrls = new ArrayList<String>(urls.size());
        for (String url : urls) {
            LinkNormalizer ln = new DefaultLinkNormalizer(hostUrl);
            String newUrl = URLCanonicalizer.getCanonicalURL(ln.normalize(url));
            if (newUrl.startsWith("mailto:"))
                continue;
            newUrls.add(newUrl);
        }

        return newUrls;
    }

}
