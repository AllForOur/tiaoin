package com.tiaoin.crawl.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tiaoin.crawl.common.enums.ValueType;

@Documented
@Retention(RetentionPolicy.RUNTIME) 
public @interface ValueAnnotation {
    
    /**
     * 类型，对应于是int，还是String
     * 
     * @return
     */
    public ValueType type() default  ValueType.TYPE_INT;
    
    public String propertyName();

    /**
     * 设置值
     * 
     * @return
     */
    public String value();
    
}
