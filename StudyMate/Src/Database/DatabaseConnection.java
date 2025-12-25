package database;

import java.sql.*;

public class DatabaseConnection 
{
    private static final String DB_URL_BASE = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "study_tracker";
    private static final String DB_URL = DB_URL_BASE + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect without database first
            Connection initialConnection = DriverManager.getConnection(DB_URL_BASE, DB_USER, DB_PASSWORD);

            // Create database if it doesn't exist
            Statement stmt = initialConnection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.close();
            initialConnection.close();

            // Now connect to the actual database
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create table
            createTableIfNotExists();
        } 
        catch (Exception e) 
        {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    private void createTableIfNotExists() 
    {
        try (Statement stmt = connection.createStatement()) 
        {
            String createTable = """
                CREATE TABLE IF NOT EXISTS study_logs (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    date DATE NOT NULL,
                    subject VARCHAR(255) NOT NULL,
                    duration DECIMAL(5,2) NOT NULL,
                    description TEXT
                )
                """;
            stmt.execute(createTable);
        } 
        catch (SQLException e) 
        {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() 
    {
        if (instance == null) 
        {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() 
    {
        try 
        {
            if (connection == null || connection.isClosed()) 
            {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error getting connection: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() 
    {
        try 
        {
            if (connection != null && !connection.isClosed()) 
            {
                connection.close();
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
