package tn.esprit.powerHR.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private static MyDataBase instance;
    private static Connection connection;
    
    private final String URL = "jdbc:mysql://127.0.0.1:3306/maha1";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private MyDataBase() {
        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to Database Successfully!");
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
        }
    }

    public static MyDataBase getInstance() {
        if(instance == null) {
            instance = new MyDataBase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (instance != null && connection != null) {
            try {
                connection.close();
                connection = null;
                instance = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection!");
                e.printStackTrace();
            }
        }
    }
}