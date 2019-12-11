package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 创建代理对象，输入一个目标类和一组 {@link Proxy} 接口实现，输出一个代理对象
 */
public class ProxyManager {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<?> targetClass, List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod, methodParams, methodProxy, proxyList).doProxyChain();
            }
        });
    }

}
