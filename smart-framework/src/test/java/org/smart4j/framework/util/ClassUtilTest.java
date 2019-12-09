package org.smart4j.framework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("类操作相关的方法测试")
class ClassUtilTest {

    @Test
    @DisplayName("获取指定包名下的所有类")
    void shouldGetClassesSuccess() {
        Set<Class<?>> classSet = ClassUtil.getClassSet("org.smart4j.framework");
        assertThat(classSet).isNotEmpty().contains(ClassUtilTest.class);

        Set<Class<?>> annotationSet = ClassUtil.getClassSet("org.smart4j.framework.annotation");
        assertThat(annotationSet).hasSize(4);
    }

    @Test
    @DisplayName("包名不存在")
    void shouldGetEmptyWhenPackageNotExist() {
        Set<Class<?>> classSet = ClassUtil.getClassSet("org.smart4j.framework.non-exist");
        assertThat(classSet).isEmpty();
    }

    @Test
    @DisplayName("包中没有类")
    void shouldGetEmptyWhenPackageHaveNothing() {
        Set<Class<?>> classSet = ClassUtil.getClassSet("org.smart4j.framework.demo.nothing");
        assertThat(classSet).isEmpty();
    }

    @Test
    @DisplayName("无效参数")
    void shouldThrowExceptionWhenPackageInvalid() {
        assertThatThrownBy(() -> ClassUtil.getClassSet("")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter should not be empty");
        assertThatThrownBy(() -> ClassUtil.getClassSet(null)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parameter should not be empty");
    }

}