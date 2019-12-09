package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomController;
import org.smart4j.framework.bean.Handler;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("管理请求与处理映射测试")
class ControllerHelperTest {

    @Test
    @DisplayName("正确解析请求与处理映射")
    void shouldGetMappingSuccess() throws ClassNotFoundException {
        Class.forName(ControllerHelper.class.getName());
        Handler handler = ControllerHelper.getHandler("get", "/custom");
        assertThat(handler).isNotNull();
        assertThat(handler.getControllerClass()).isEqualTo(CustomController.class);
        assertThat(handler.getActionMethod().getName()).isEqualTo("custom");
    }

}