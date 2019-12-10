package org.smart4j.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 实现依赖注入
 */
public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    /**
     * （1）通过 {@link BeanHelper#getBeanMap} 获取 Bean 类与 Bean 实例之间的映射关系
     * （2）遍历这个映射关系，分别取出 Bean 类与 Bean 实例，进而通过反射获取类中所有的成员变量
     * （3）继续遍历这些成员变量，在循环中判断当前成员变量是否带有 {@link Inject} 注解，若带有该注解，则从 Bean Map 中取出 Bean 实例，
     * 最后通过 {@link ReflectionUtil#setField} 方法来修改当前成员变量的值
     */
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            } else {
                                LOGGER.error("Smart framework do not contains bean instance of class: " + beanFieldClass.getName());
                                throw new RuntimeException("Bean instance does not exist");
                            }
                        }
                    }
                } else {
                    LOGGER.warn("This bean class does not have any fields: " + beanClass.getName());
                }
            }
        } else {
            LOGGER.warn("Smart framework does not manage any mapping between bean class and bean instance");
        }
    }

}
