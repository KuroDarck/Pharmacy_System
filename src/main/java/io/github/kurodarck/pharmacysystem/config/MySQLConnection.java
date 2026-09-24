package io.github.kurodarck.pharmacysystem.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {
    private static final Properties properties = new Properties();
    private static Connection connection = null;

    static {
        try (InputStream inputStream = MySQLConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IOException("It was imposible to Find config.properties file");
            }
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
            if (connection == null || connection.isClosed()) {
                String username = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");
                String url = properties.getProperty("db.url");
                connection = DriverManager.getConnection(url, username, password);
            }
        return connection;
    }

    private MySQLConnection() {
    }
}
