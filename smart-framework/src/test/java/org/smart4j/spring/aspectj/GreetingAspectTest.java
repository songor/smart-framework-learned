package org.smart4j.spring.aspectj;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.spring.Apology;
import org.smart4j.spring.Greeting;
import org.smart4j.spring.GreetingImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@DisplayName("Spring 集成 AspectJ 测试")
class GreetingAspectTest {

    @Test
    @DisplayName("测试通过 AspectJ execution 表达式拦截方法")
    void testAspectJExecution() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aspectj.xml");
        GreetingImpl greeting = (GreetingImpl) context.getBean("greetingImpl");
        greeting.goodMorning("Spring AspectJ");
        greeting.goodNight("Spring AspectJ");
    }

    @Test
    @DisplayName("测试通过 AspectJ @annotation 表达式拦截方法")
    void testAspectJAnnotation() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aspectj.xml");
        Greeting greeting = (Greeting) context.getBean("greetingImpl");
        greeting.sayHello("Spring AspectJ");
    }

    @Test
    @DisplayName("测试引入增强")
    void testDeclareParents() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aspectj.xml");
        Greeting greeting = (Greeting) context.getBean("greetingImpl");
        Apology apology = (Apology) greeting;
        apology.saySorry("Spring AspectJ");
    }

}
