package org.smart4j.demo;

import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class CustomService {

    @Transaction
    public void insertCustomMessages(List<String> messages) throws SQLException {
        Connection connection = DatabaseHelper.CONNECTION_HOLDER.get();
        String sql = "insert into CUSTOM(MESSAGE) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (String message : messages) {
            statement.setString(1, message);
            statement.executeUpdate();
        }
    }

    @Transaction
    public void insertCustomMessagesWithEx(String message) throws SQLException {
        Connection connection = DatabaseHelper.CONNECTION_HOLDER.get();
        String sql = "insert into CUSTOM(MESSAGE) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, message);
        statement.executeUpdate();
        throw new RuntimeException("Custom exception");
    }

}
