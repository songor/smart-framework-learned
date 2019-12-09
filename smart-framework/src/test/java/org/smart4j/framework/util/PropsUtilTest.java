package org.smart4j.framework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.framework.ConfigConstant;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("读取 properties 配置文件测试")
class PropsUtilTest {

    @Test
    @DisplayName("正确加载 properties 配置文件")
    void shouldLoadPropsSuccess() {
        Properties props = PropsUtil.loadProps("smart.properties");
        assertThat(props).containsKeys(ConfigConstant.JDBC_DRIVER, ConfigConstant.JDBC_URL,
                ConfigConstant.JDBC_USERNAME, ConfigConstant.JDBC_PASSWORD);
    }

    @Test
    @DisplayName("加载的 properties 配置文件不存在")
    void shouldThrowExceptionWhenFileNotExist() {
        final String nonExistPropsFile = "smart-non-exist.properties";
        assertThatThrownBy(() -> PropsUtil.loadProps(nonExistPropsFile)).isInstanceOf(RuntimeException.class)
                .hasMessage("File don't exist");
    }

    @Test
    @DisplayName("获取字符型属性真实值")
    void shouldGetActualValueWhenExist() {
        Properties props = new Properties();
        props.put("key", "value");
        assertThat("value").isEqualTo(PropsUtil.getString(props, "key"));
    }

    @Test
    @DisplayName("获取字符型属性默认值")
    void shouldGetEmptyWhenNonExist() {
        assertThat("").isEqualTo(PropsUtil.getString(new Properties(), "non-exist-key"));
    }

}