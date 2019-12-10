package org.smart4j.proxy.cglib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.proxy.Hello;
import org.smart4j.proxy.HelloImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CGLib 动态代理测试")
class CGLibDynamicProxyTest {

    @Test
    public void test() {
        Hello proxy = CGLibDynamicProxy.getInstance().getProxy(HelloImpl.class);
        assertThat(proxy.hello("CGLib")).isEqualTo("Hello CGLib");
    }

}