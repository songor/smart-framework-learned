package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomController;
import org.smart4j.demo.CustomService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("管理 Bean 测试")
class BeanHelperTest {

    @Test
    @DisplayName("获取被 Smart 框架管理的 Bean 映射")
    void getBeanMap() {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        assertThat(beanMap).isNotEmpty().containsKeys(CustomController.class, CustomService.class);
    }

}