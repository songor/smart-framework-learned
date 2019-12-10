package org.smart4j.spring.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.smart4j.spring.Apology;
import org.springframework.stereotype.Component;

/**
 * Aspect 其实就是 Advisor
 */
@Aspect
@Component
public class GreetingAspect {

    /**
     * 基于注解：通过 AspectJ execution 表达式拦截方法
     */
    /**
     * 第一个 * 表示方法的返回值是任意的
     * 第二个 * 表示匹配该类中的所有方法
     * (..) 表示方法的参数是任意的
     */
    @Around("execution(* org.smart4j.spring.GreetingImpl.good*(..))")
    public Object executionAround(ProceedingJoinPoint pjp) throws Throwable {
        before();
        Object result = pjp.proceed();
        after();
        return result;
    }

    /**
     * 基于注解：通过 AspectJ @annotation 表达式拦截方法
     */
    @Around("@annotation(org.smart4j.spring.aspectj.Tag)")
    public Object annotationAround(ProceedingJoinPoint pjp) throws Throwable {
        before();
        Object result = pjp.proceed();
        after();
        return result;
    }

    private void before() {
        System.out.println("Before");
    }

    private void after() {
        System.out.println("After");
    }

    /**
     * 引入增强
     */
    @DeclareParents(value = "org.smart4j.spring.GreetingImpl", defaultImpl = ApologyImpl.class)
    private Apology apology;

}
