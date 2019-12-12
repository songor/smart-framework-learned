package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomController;
import org.smart4j.framework.bean.Handler;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("维护请求与处理器的映射关系测试")
class ControllerHelperTest {

    @Test
    @DisplayName("解析 CustomController 中请求与处理器的映射关系")
    public void shouldGetHandlerSuccess() throws ClassNotFoundException {
        Class.forName(ControllerHelper.class.getName());
        Handler handler = ControllerHelper.getHandler("get", "/custom");
        assertThat(handler).isNotNull();
        assertThat(handler.getControllerClass()).isEqualTo(CustomController.class);
        assertThat(handler.getActionMethod().getName()).isEqualTo("custom");

        handler = ControllerHelper.getHandler("get", "/custom_with_param");
        assertThat(handler).isNotNull();
        assertThat(handler.getControllerClass()).isEqualTo(CustomController.class);
        assertThat(handler.getActionMethod().getName()).isEqualTo("customWithParam");
    }

}