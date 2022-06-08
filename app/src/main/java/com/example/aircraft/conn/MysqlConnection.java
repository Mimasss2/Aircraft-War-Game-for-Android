package com.example.aircraft.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnection {public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.250.195.197:3306/aircraftwar?useSSL=false" +
            "&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static final String USER = "remote_user";
    public static final String PASS = "123456";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        // register jdbc driver
        Class.forName(JDBC_DRIVER);

        // open database
        System.out.println("Connecting database");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        System.out.println("Database Connect Success!");
        return conn;
    }

    public static void closeConnection(Connection conn) throws SQLException {
        if(conn != null) {
            conn.close();
        }
    }
    public static void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
}
