package org.smart4j.spring.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.spring.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Spring AOP 增强测试")
class AdviceTest {

    @Test
    @DisplayName("测试前置、后置、环绕增强（编程式）")
    void testImperativeAdvice() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(Greeting.class);
        proxyFactory.setTarget(new GreetingImpl());
        proxyFactory.addAdvice(new GreetingBeforeAdvice());
        proxyFactory.addAdvice(new GreetingAfterAdvice());
        proxyFactory.addAdvice(new GreetingAroundAdvice());
        Greeting greeting = (Greeting) proxyFactory.getProxy();
        greeting.sayHello("Spring AOP");
    }

    @Test
    @DisplayName("测试抛出增强（编程式）")
    void testImperativeThrowAdvice() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(Greeting.class);
        proxyFactory.setTarget(new GreetingThrowImpl());
        proxyFactory.addAdvice(new GreetingThrowAdvice());
        Greeting greeting = (Greeting) proxyFactory.getProxy();
        assertThatThrownBy(() -> greeting.sayHello("")).isInstanceOf(RuntimeException.class)
                .hasMessage("An error occurred");
    }

    @Test
    @DisplayName("测试引入增强（编程式）")
    void testImperativeIntroAdvice() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(Apology.class);
        proxyFactory.setTarget(new GreetingImpl());
        proxyFactory.addAdvice(new GreetingIntroAdvice());
        // true，代理目标类，CGLib 动态代理
        // false，代理接口，JDK 动态代理
        proxyFactory.setProxyTargetClass(true);
        Greeting greeting = (Greeting) proxyFactory.getProxy();
        greeting.sayHello("Spring AOP");

        Apology apology = (Apology) greeting;
        apology.saySorry("Spring AOP");
    }

    @Test
    @DisplayName("测试前置、后置、环绕增强（声明式）")
    void testDeclarativeAdvice() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Greeting greeting = (Greeting) context.getBean("greetingProxy");
        greeting.sayHello("Spring AOP");
    }

    @Test
    @DisplayName("测试抛出增强（声明式）")
    void testDeclarativeThrowAdvice() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Greeting greeting = (Greeting) context.getBean("greetingThrowProxy");
        assertThatThrownBy(() -> greeting.sayHello("")).isInstanceOf(RuntimeException.class).hasMessage("An error occurred");
    }

    @Test
    @DisplayName("测试引入增强（声明式）")
    void testDeclarativeIntroAdvice() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Greeting greeting = (Greeting) context.getBean("greetingIntroProxy");
        greeting.sayHello("Spring AOP");

        Apology apology = (Apology) greeting;
        apology.saySorry("Spring AOP");
    }

    @Test
    @DisplayName("测试切面")
    void testAdvisor() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GreetingImpl greeting = (GreetingImpl) context.getBean("greetingProxyWithAdvisor");
        greeting.sayHello("Spring AOP");

        greeting.goodMorning("Spring AOP");
        greeting.goodNight("Spring AOP");
    }

    @Test
    @DisplayName("测试自动代理")
    void testAutoProxy() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GreetingAutoImpl greeting = (GreetingAutoImpl) context.getBean("greetingAutoImpl");
        greeting.sayHi("Spring AOP");
    }

}
