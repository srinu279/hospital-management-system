package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
	/**
	 * Singleton class for database connection
	 */

	    private static final String URL = "jdbc:mysql://localhost:3306/hospital_management";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "tiger";
	    
	    private static Connection connection = null;
	    
	    private DatabaseConnection() {
	        // Private constructor to prevent instantiation
	    }
	    
	    public static Connection getConnection() {
	        if (connection == null) {
	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	                System.out.println("Database connection established successfully");
	            } catch (ClassNotFoundException e) {
	                System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
	            } catch (SQLException e) {
	                System.err.println("Connection failed: " + e.getMessage());
	            }
	        }
	        return connection;
	    }
	    
	    public static void closeConnection() {
	        if (connection != null) {
	            try {
	                connection.close();
	                connection = null;
	                System.out.println("Database connection closed successfully");
	            } catch (SQLException e) {
	                System.err.println("Error closing connection: " + e.getMessage());
	            }
	        }
	    }
	

	// Example usage:
	// Connection conn = DatabaseConnection.getConnection();
	// ... use the connection
	// DatabaseConnection.closeConnection();
}



