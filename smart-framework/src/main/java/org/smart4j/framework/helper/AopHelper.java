package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 初始化 AOP 框架
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
        Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
        for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
            Class<?> targetClass = entry.getKey();
            List<Proxy> proxyList = entry.getValue();
            Object proxy = ProxyManager.createProxy(targetClass, proxyList);
            BeanHelper.setBean(targetClass, proxy);
        }
    }

    /**
     * 获取代理类（Advice、Aspect）及其目标类集合之间的映射关系，一个代理类可对应一个或多个目标类。
     * 代理类需要扩展 {@link AspectProxy} 抽象类，还需要带有 {@link Aspect} 注解，只有满足这两个条件，
     * 才能根据 {@link Aspect} 注解中所定义的注解属性去获取该注解所对应的目标类集合，然后才能建立代理类与目标类集合之间的映射关系。
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Class<? extends Annotation> annotation = aspect.value();
                if (annotation == Aspect.class) {
                    LOGGER.error("class: " + proxyClass.getName() + ", annotation: " + annotation.getName());
                    throw new RuntimeException("Incorrect annotation");
                } else {
                    Set<Class<?>> targetClassSet = ClassHelper.getClassSetByAnnotation(annotation);
                    proxyMap.put(proxyClass, targetClassSet);
                }
            } else {
                LOGGER.warn("This proxy class is not working because of missing annotation @Aspect: " + proxyClass.getName());
            }
        }
        return proxyMap;
    }

    /**
     * 目标类与代理对象集合之间的映射关系
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy;
                try {
                    proxy = (Proxy) proxyClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

}
