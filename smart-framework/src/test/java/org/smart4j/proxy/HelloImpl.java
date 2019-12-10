package org.smart4j.proxy;

public class HelloImpl implements Hello {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

}
