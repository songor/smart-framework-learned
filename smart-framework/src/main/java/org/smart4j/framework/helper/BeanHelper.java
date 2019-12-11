package org.smart4j.framework.helper;

import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 维护 Bean 类与 Bean 实例的映射关系
 */
public final class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    /**
     * 首先获取所有被 Smart 框架管理的 Bean 类，此时需要调用 {@link ClassHelper#getBeanClassSet} 方法，
     * 随后循环调用 {@link ReflectionUtil#newInstance} 方法，根据类来实例化对象，
     * 最后将每次创建的对象存放在一个静态的 Map 中。
     */
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("Can't get bean by class " + cls.getName());
        }
        return (T) BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }

}
