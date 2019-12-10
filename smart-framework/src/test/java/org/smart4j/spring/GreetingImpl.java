package org.smart4j.spring;

import org.smart4j.spring.aspectj.Tag;
import org.springframework.stereotype.Component;

@Component
public class GreetingImpl implements Greeting {

    @Tag
    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }

    public void goodMorning(String name) {
        System.out.println("Good Morning " + name);
    }

    public void goodNight(String name) {
        System.out.println("Good Night " + name);
    }

}
