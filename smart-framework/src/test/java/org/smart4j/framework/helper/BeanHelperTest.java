package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomController;
import org.smart4j.demo.CustomService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("维护 Bean 类与 Bean 实例的映射关系测试")
class BeanHelperTest {

    @Test
    @DisplayName("获取被 Smart 框架维护的 Bean 类与 Bean 实例映射")
    void shouldGetBeanMapSuccess() {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        assertThat(beanMap).isNotEmpty();
    }

    @Test
    @DisplayName("获取被 Smart 框架维护的 Bean 实例")
    void shouldGetBeanSuccess() {
        assertThat(BeanHelper.getBean(CustomController.class)).isNotNull();
        assertThat(BeanHelper.getBean(CustomService.class)).isNotNull();
    }

    @Test
    @DisplayName("获取未被 Smart 框架维护的 Bean 实例")
    void shouldThrowExceptionWhenBeanNotExist() {
        assertThatThrownBy(() -> BeanHelper.getBean(BeanHelperTest.class)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Can't get bean by class");
    }

}