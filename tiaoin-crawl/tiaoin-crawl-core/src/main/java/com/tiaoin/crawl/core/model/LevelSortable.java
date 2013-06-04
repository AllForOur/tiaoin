package com.tiaoin.crawl.core.model;

/**
 * 排序接口
 * 
 * @author sky.yang
 * @version $Id: LevelSortable.java, v 1.0 2013-6-1 下午6:36:06 sky.yang Exp $
 */
public interface LevelSortable {

    /**
     * 获取处理级别
     * @return
     */
    int getLevel();
    
    /**
     * 设置级别
     * 
     * @param level
     */
    void setLevel(int level);
}
