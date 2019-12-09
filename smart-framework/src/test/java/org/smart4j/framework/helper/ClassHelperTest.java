package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomController;
import org.smart4j.demo.CustomService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("获取应用包名下的类测试")
class ClassHelperTest {

    @Test
    @DisplayName("获取 org.smart4j.demo 下所有类")
    void shouldGetClassSetSuccess() {
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        assertThat(classSet).isNotEmpty();
    }

    @Test
    @DisplayName("获取 org.smart4j.demo 下所有 Controller 类")
    void shouldGetControllerClassSetSuccess() {
        Set<Class<?>> classSet = ClassHelper.getControllerClassSet();
        assertThat(classSet).isNotEmpty().contains(CustomController.class);
    }

    @Test
    @DisplayName("获取 org.smart4j.demo 下所有 Service 类")
    void shouldGetServiceClassSetSuccess() {
        Set<Class<?>> classSet = ClassHelper.getServiceClassSet();
        assertThat(classSet).isNotEmpty().contains(CustomService.class);
    }

    @Test
    @DisplayName("获取 org.smart4j.demo 下所有 Controller 类和 Service 类")
    void shouldGetBeanClassSetSuccess() {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        assertThat(classSet).isNotEmpty().contains(CustomController.class, CustomService.class);
    }

}