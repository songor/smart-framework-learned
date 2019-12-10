package org.smart4j.proxy.cglib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.proxy.Hello;
import org.smart4j.proxy.HelloImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CGLib 代理测试")
class CGLibProxyTest {

    @Test
    public void test() {
        Hello helloProxy = CGLibProxy.getInstance().getProxy(HelloImpl.class);
        assertThat(helloProxy.hello("CGLib")).isEqualTo("Hello CGLib");
    }

}