package com.test.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/qdqg","root","123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
