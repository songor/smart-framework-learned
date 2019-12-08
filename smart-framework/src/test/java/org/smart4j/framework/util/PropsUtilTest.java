package org.smart4j.framework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.framework.ConfigConstant;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Properties 工具类测试")
class PropsUtilTest {

    @Test
    @DisplayName("正确加载 Properties 文件")
    void shouldLoadPropsSuccessWhenVaildFile() {
        final String validPropsFile = "smart.properties";
        Properties props = PropsUtil.loadProps(validPropsFile);
        assertThat(props).containsKeys(ConfigConstant.JDBC_DRIVER, ConfigConstant.JDBC_URL,
                ConfigConstant.JDBC_USERNAME, ConfigConstant.JDBC_PASSWORD);
    }

    @Test
    @DisplayName("不存在的 Properties 文件")
    void shouldThrowExceptionWhenNonExistFile() {
        final String nonExistPropsFile = "smart-non-exist.properties";
        assertThatThrownBy(() -> PropsUtil.loadProps(nonExistPropsFile)).isInstanceOf(RuntimeException.class)
                .hasMessage("File don't exist");
    }

    @Test
    @DisplayName("getString() 方法返回真实值")
    void shouldGetActualValueWhenExist() {
        Properties props = new Properties();
        props.put("key", "value");
        assertThat("value").isEqualTo(PropsUtil.getString(props, "key"));
    }

    @Test
    @DisplayName("getString() 方法返回默认值")
    void shouldGetEmptyWhenNonExist() {
        assertThat("").isEqualTo(PropsUtil.getString(new Properties(), "non-exist-key"));
    }

}