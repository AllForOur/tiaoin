package com.tiaoin.crawl.core.spider;

import java.util.Comparator;

import com.tiaoin.crawl.core.xml.Impl;

public class ImplComparator implements Comparator<Impl> {

    @Override
    public int compare(Impl o1, Impl o2) {
      //注意这里是优先级，1 < 0 < -1 
        int sort1 = Integer.parseInt(o1.getSort());
        int sort2 = Integer.parseInt(o2.getSort());
        if (sort1 == sort2)
            return 0;
        return sort1 > sort2 ? 1 : -1;
    }

}
