package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    public static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    static {
        try {
            Class.forName(ConfigHelper.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword());
            } catch (SQLException e) {
                LOGGER.error("url: {}, user: {}, password: {}",
                        ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword());
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void rollbackTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
