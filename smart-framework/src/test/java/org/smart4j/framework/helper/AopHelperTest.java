package org.smart4j.framework.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.smart4j.demo.CustomService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("初始化 AOP 框架测试")
class AopHelperTest {

    @Test
    @DisplayName("提交事务")
    void shouldInsertMessagesSuccess() throws ClassNotFoundException, SQLException {
        Class.forName(AopHelper.class.getName());
        CustomService service = BeanHelper.getBean(CustomService.class);
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(UUID.randomUUID().toString());
        }
        service.insertCustomMessages(messages);
        assertThat(isContainsMessages(messages)).isTrue();
    }

    @Test
    @DisplayName("回滚事务")
    void shouldRollbackMessageSuccess() throws ClassNotFoundException, SQLException {
        Class.forName(AopHelper.class.getName());
        CustomService service = BeanHelper.getBean(CustomService.class);
        String message = UUID.randomUUID().toString();
        try {
            service.insertCustomMessagesWithEx(message);
        } catch (Exception e) {
            assertThat(e.getCause()).isInstanceOf(RuntimeException.class).hasMessage("Custom exception");
        }
        List<String> messages = new ArrayList<>();
        messages.add(message);
        assertThat(isContainsMessages(messages)).isFalse();
    }

    boolean isContainsMessages(List<String> messages) throws SQLException {
        Connection connection;
        int total = 0;
        try {
            connection = DatabaseHelper.getConnection();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < messages.size(); i++) {
                sb.append("?,");
            }
            String sql = "select count(*) as total from CUSTOM where message in (" + sb.deleteCharAt(sb.length() - 1).toString() + ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 0;
            for (String message : messages) {
                statement.setString(++index, message);
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } finally {
            DatabaseHelper.closeConnection();
        }
        return total == messages.size();
    }

}
