package org.smart4j.spring;

import org.springframework.stereotype.Component;

@Component
public class GreetingThrowImpl implements Greeting {

    @Override
    public void sayHello(String name) {
        throw new RuntimeException("An error occurred");
    }

}
