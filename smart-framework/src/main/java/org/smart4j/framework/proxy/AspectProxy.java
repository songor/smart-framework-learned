package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

/**
 * Advice Template
 */
public abstract class AspectProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    protected void begin() {
    }

    protected boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    protected void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    protected void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
    }

    protected void error(Class<?> cls, Method method, Object[] params, Exception e) throws Throwable {
    }

    protected void end() {
    }

}
