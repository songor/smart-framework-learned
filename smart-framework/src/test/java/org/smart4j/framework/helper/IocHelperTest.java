package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomController;
import org.smart4j.demo.CustomService;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("依赖注入测试")
class IocHelperTest {

    @Test
    @DisplayName("在 CustomController 中注入 CustomService")
    void shouldInjectSuccess() throws ClassNotFoundException {
        Class.forName(IocHelper.class.getName());
        CustomController customController = BeanHelper.getBean(CustomController.class);
        CustomService customService = customController.getCustomService();
        assertThat(customService).isNotNull().isEqualTo(BeanHelper.getBean(CustomService.class));
    }

}