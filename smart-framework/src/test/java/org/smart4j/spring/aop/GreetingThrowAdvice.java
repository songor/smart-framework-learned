package org.smart4j.spring.aop;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 抛出增强
 */
@Component
public class GreetingThrowAdvice implements ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.println("Target class: " + target.getClass().getName());
        System.out.println("Method name: " + method.getName());
        System.out.println("Exception message: " + ex.getMessage());
    }

}
