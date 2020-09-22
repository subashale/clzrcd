package com.clzrcd.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3606/studentdb",
                    "root", "");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;

    }
}
