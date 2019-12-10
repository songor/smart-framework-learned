package org.smart4j.proxy.stat;

import org.smart4j.proxy.Hello;

/**
 * 静态代理
 */
public class HelloProxy implements Hello {

    private Hello hello;

    public HelloProxy(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String hello(String name) {
        before();
        String result = hello.hello(name);
        after();
        return result;
    }

    private void before() {
    }

    private void after() {
    }

}
