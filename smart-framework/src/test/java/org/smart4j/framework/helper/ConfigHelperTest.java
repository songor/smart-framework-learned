package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("读取 smart.properties 配置文件中的配置项测试")
class ConfigHelperTest {

    @Test
    @DisplayName("读取配置项 smart.framework.jdbc.driver")
    void shouldGetJdbcDriverSuccess() {
        assertThat("com.mysql.cj.jdbc.Driver").isEqualTo(ConfigHelper.getJdbcDriver());
    }

    @Test
    @DisplayName("读取配置项 smart.framework.jdbc.url")
    void shouldGetJdbcUrlSuccess() {
        assertThat("jdbc:mysql://localhost:3306/demo").isEqualTo(ConfigHelper.getJdbcUrl());
    }

    @Test
    @DisplayName("读取配置项 smart.framework.jdbc.username")
    void shouldGetJdbcUsernameSuccess() {
        assertThat("root").isEqualTo(ConfigHelper.getJdbcUsername());
    }

    @Test
    @DisplayName("读取配置项 smart.framework.jdbc.password")
    void shouldGetJdbcPasswordSuccess() {
        assertThat("root").isEqualTo(ConfigHelper.getJdbcPassword());
    }

    @Test
    @DisplayName("读取配置项 smart.framework.app.base_package")
    void shouldGetAppBasePackageSuccess() {
        assertThat("org.smart4j.demo").isEqualTo(ConfigHelper.getAppBasePackage());
    }

    @Test
    @DisplayName("读取配置项 smart.framework.app.jsp_path")
    void shouldGetAppJspPathSuccess() {
        assertThat("/WEB-INF/view/").isEqualTo(ConfigHelper.getAppJspPath());
    }

    @Test
    @DisplayName("读取配置项 smart.framework.app.asset_path")
    void shouldGetAppAssetPathSuccess() {
        assertThat("/asset/").isEqualTo(ConfigHelper.getAppAssetPath());
    }

}