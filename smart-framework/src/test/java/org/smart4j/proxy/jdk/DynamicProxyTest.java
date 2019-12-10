package org.smart4j.proxy.jdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.proxy.Hello;
import org.smart4j.proxy.HelloImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JDK 动态代理测试")
public class DynamicProxyTest {

    @Test
    public void test() {
        DynamicProxy dynamicProxy = new DynamicProxy(new HelloImpl());
        Hello helloProxy = dynamicProxy.getProxy();
        assertThat(helloProxy.hello("JDK")).isEqualTo("Hello JDK");
    }

}
