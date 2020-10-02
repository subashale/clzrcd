package com.clzrcd.db;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    public static Connection getDBConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clzrec",
                    "root", "");

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
