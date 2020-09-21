package com.swingdb.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getDBConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb",
                    "root", "");

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
