package org.smart4j.framework.proxy;

/**
 * Advice
 */
public interface Proxy {

    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
