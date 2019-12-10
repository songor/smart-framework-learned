package org.smart4j.proxy.stat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.proxy.HelloImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("静态代理测试")
class HelloProxyTest {

    @Test
    public void test() {
        HelloProxy proxy = new HelloProxy(new HelloImpl());
        assertThat(proxy.hello("Static")).isEqualTo("Hello Static");
    }

}