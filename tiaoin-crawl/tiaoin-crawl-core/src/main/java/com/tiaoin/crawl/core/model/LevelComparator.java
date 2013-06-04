package com.tiaoin.crawl.core.model;

import java.util.Comparator;

/**
 * 排序处理器
 * level越小的,级别越高，越高的优先加载启动
 * 
 * @author sky.yang
 * @version $Id: LevelComparator.java, v 1.0 2013-6-1 下午6:37:25 sky.yang Exp $
 */
public class LevelComparator implements Comparator<LevelSortable> {

    @Override
    public int compare(LevelSortable arg0, LevelSortable arg1) {
        int level1 = arg0.getLevel();
        int level2 = arg1.getLevel();
        return -(level1 - level2);
    }

}
