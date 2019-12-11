package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理链
 */
public class ProxyChain {

    private final Class<?> targetClass;

    private final Object targetObject;

    private final Method targetMethod;

    private final Object[] methodParams;

    private final MethodProxy methodProxy;

    private final List<Proxy> proxyList;

    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams,
                      MethodProxy methodProxy, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    /**
     * 通过 {@link ProxyChain#proxyIndex} 来充当代理对象的计数器，若尚未达到 {@link ProxyChain#proxyList} 的上限，
     * 则从中取出相应的 {@link Proxy} 对象，并调用其 {@link Proxy#doProxy} 方法。
     * 在 {@link Proxy} 接口的实现中会提供相应的横切逻辑，并调用 {@link ProxyChain#doProxyChain} 方法，
     * 直到 {@link ProxyChain#proxyIndex} 达到 {@link ProxyChain#proxyList} 的上限为止，最后调用 {@link MethodProxy#invokeSuper)} 方法，
     * 执行目标对象的业务逻辑。
     */
    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return result;
    }

}
