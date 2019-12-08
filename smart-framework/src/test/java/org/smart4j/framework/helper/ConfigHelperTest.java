package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("读取 smart.properties 配置参数测试")
class ConfigHelperTest {

    @Test
    @DisplayName("读取参数 smart.framework.jdbc.driver")
    void shouldGetActualJdbcDriverWhenConfig() {
        assertThat("com.mysql.jdbc.Driver").isEqualTo(ConfigHelper.getJdbcDriver());
    }

    @Test
    @DisplayName("读取参数 smart.framework.jdbc.url")
    void shouldGetActualJdbcUrlWhenConfig() {
        assertThat("jdbc:mysql://localhost:3306/demo").isEqualTo(ConfigHelper.getJdbcUrl());
    }

    @Test
    @DisplayName("读取参数 smart.framework.jdbc.username")
    void shouldGetActualJdbcUsernameWhenConfig() {
        assertThat("root").isEqualTo(ConfigHelper.getJdbcUsername());
    }

    @Test
    @DisplayName("读取参数 smart.framework.jdbc.password")
    void shouldGetActualJdbcPasswordWhenConfig() {
        assertThat("root").isEqualTo(ConfigHelper.getJdbcPassword());
    }

    @Test
    @DisplayName("读取参数 smart.framework.app.base_package")
    void shouldGetActualAppBasePackageWhenConfig() {
        assertThat("org.smart4j.demo").isEqualTo(ConfigHelper.getAppBasePackage());
    }

    @Test
    @DisplayName("读取参数 smart.framework.app.jsp_path")
    void shouldGetActualAppJspPathWhenConfig() {
        assertThat("/WEB-INF/view/").isEqualTo(ConfigHelper.getAppJspPath());
    }

    @Test
    @DisplayName("读取参数 smart.framework.app.asset_path")
    void shouldGetActualAppAssetPathWhenConfig() {
        assertThat("/asset/").isEqualTo(ConfigHelper.getAppAssetPath());
    }

}