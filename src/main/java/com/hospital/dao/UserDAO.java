package com.hospital.dao;
import com.hospital.model.User;
import com.hospital.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO
{
	

	

	/**
	 * Data Access Object for User entity
	 */
	
	    private Connection connection;
	    
	    public UserDAO() {
	        this.connection = DatabaseConnection.getConnection();
	    }
	    
	    /**
	     * Add a new user to the database
	     */
	    public int addUser(User user) throws SQLException {
	        String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, user.getName());
	            statement.setString(2, user.getEmail());
	            statement.setString(3, user.getPassword());
	            statement.setString(4, user.getRole());
	            
	            int affectedRows = statement.executeUpdate();
	            
	            if (affectedRows == 0) {
	                throw new SQLException("Creating user failed, no rows affected.");
	            }
	            
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating user failed, no ID obtained.");
	                }
	            }
	        }
	    }
	    
	    /**
	     * Get a user by ID
	     */
	    public User getUserById(int id) throws SQLException {
	        String query = "SELECT * FROM users WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractUserFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get a user by email
	     */
	    public User getUserByEmail(String email) throws SQLException {
	        String query = "SELECT * FROM users WHERE email = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, email);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractUserFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get all users
	     */
	    public List<User> getAllUsers() throws SQLException {
	        List<User> users = new ArrayList<>();
	        String query = "SELECT * FROM users";
	        
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {
	            
	            while (resultSet.next()) {
	                users.add(extractUserFromResultSet(resultSet));
	            }
	        }
	        
	        return users;
	    }
	    
	    /**
	     * Update a user
	     */
	    public boolean updateUser(User user) throws SQLException {
	        String query = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, user.getName());
	            statement.setString(2, user.getEmail());
	            statement.setString(3, user.getPassword());
	            statement.setString(4, user.getRole());
	            statement.setInt(5, user.getId());
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Delete a user
	     */
	    public boolean deleteUser(int id) throws SQLException {
	        String query = "DELETE FROM users WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Authenticate a user
	     */
	    public User authenticateUser(String email, String password) throws SQLException {
	        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, email);
	            statement.setString(2, password);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractUserFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Helper method to extract a User from a ResultSet
	     */
	    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
	        User user = new User();
	        user.setId(resultSet.getInt("id"));
	        user.setName(resultSet.getString("name"));
	        user.setEmail(resultSet.getString("email"));
	        user.setPassword(resultSet.getString("password"));
	        user.setRole(resultSet.getString("role"));
	        return user;
	    }

}
