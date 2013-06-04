package com.tiaoin.crawl.common.interceptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.tiaoin.crawl.common.annotation.ValueAnnotation;
import com.tiaoin.crawl.common.enums.ValueType;

/**
 * 值设置拦截器
 * 
 * @author sky.yang
 * @version $Id: ValueInterceptor.java, v 1.0 2013-6-2 下午5:19:15 sky.yang Exp $
 */
public class ValueInterceptor implements MethodInterceptor {
    private static final Logger logger = Logger.getLogger(ValueInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ValueAnnotation valueAnnotation = findAnnotation(invocation);
        if (null != valueAnnotation) {
            ValueType valueType = valueAnnotation.type();
            String propertyName = valueAnnotation.propertyName();
            Class clazz = invocation.getThis().getClass();
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, clazz);
            Method method = pd.getWriteMethod();
            if (valueType.equals(ValueType.TYPE_INT)) {
                int intValue = Integer.parseInt(valueAnnotation.value());
                method.invoke(invocation.getThis(), intValue);
            } else if (valueType.equals(ValueType.TYPE_STRING)) {
                String stringValue = valueAnnotation.value();
                method.invoke(invocation.getThis(), stringValue);
            }
            
            return invocation.proceed();
        }
        return null;
    }

    protected ValueAnnotation findAnnotation(MethodInvocation invocation) {
        ValueAnnotation valueAnnotation = null;
        String name = invocation.getThis().getClass().getName();
        try {
            Class clazz = Class.forName(name);

            valueAnnotation = (ValueAnnotation) clazz.getAnnotation(ValueAnnotation.class);
            if (null == valueAnnotation) {
                name = invocation.getMethod().getDeclaringClass().getName();
                clazz = Class.forName(name);
                valueAnnotation = (ValueAnnotation) clazz.getAnnotation(ValueAnnotation.class);
            }
            if (valueAnnotation == null) {
                Method method = invocation.getMethod();
                valueAnnotation = invocation.getThis().getClass()
                    .getMethod(method.getName(), method.getParameterTypes())
                    .getAnnotation(ValueAnnotation.class);
            }

        } catch (Exception e) {
            logger.error("查找ValueAnnotation注解失败", e);
        }
        return valueAnnotation;
    }

}
