package com.pdfapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/pdfdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Rohini8801#"; // <-- change this

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
