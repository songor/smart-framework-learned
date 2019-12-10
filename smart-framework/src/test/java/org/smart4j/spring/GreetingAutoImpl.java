package org.smart4j.spring;

import org.springframework.stereotype.Component;

@Component
public class GreetingAutoImpl {

    public void sayHi(String name) {
        System.out.println("Hi " + name);
    }

}
