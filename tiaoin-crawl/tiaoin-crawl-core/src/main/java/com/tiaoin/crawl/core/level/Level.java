package com.tiaoin.crawl.core.level;

/**
 * 组件级别，用于启动加载顺序
 * 
 * @author sky.yang
 * @version $Id: Level.java, v 1.0 2013-5-29 上午9:26:34 sky.yang Exp $
 */
public enum Level {

    FIRST_LEVEL(1, "优先级1,这个是最高优先级"),
    SECOND_LEVEL(2, "优先级2"),
    THIRD_LEVEL(3, "优先级3"),
    FORTH_LEVEL(4, "优先级4"),
    ;
    
    private int level;
    private String desc;
    
    private Level(int level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
