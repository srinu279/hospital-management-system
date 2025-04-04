package com.hospital.service;

import com.hospital.dao.UserDAO;
import com.hospital.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService
{

	/**
	 * Service class for User operations
	 */
	
	    private UserDAO userDAO;
	    
	    public UserService() {
	        this.userDAO = new UserDAO();
	    }
	    
	    /**
	     * Register a new user
	     */
	    public int registerUser(User user) throws SQLException {
	        // Check if email already exists
	        User existingUser = userDAO.getUserByEmail(user.getEmail());
	        if (existingUser != null) {
	            throw new IllegalArgumentException("Email already exists");
	        }
	        
	        // Add user to database
	        return userDAO.addUser(user);
	    }
	    
	    /**
	     * Authenticate a user
	     */
	    public User login(String email, String password) throws SQLException {
	        User user = userDAO.authenticateUser(email, password);
	        if (user == null) {
	            throw new IllegalArgumentException("Invalid email or password");
	        }
	        return user;
	    }
	    
	    /**
	     * Get a user by ID
	     */
	    public User getUserById(int id) throws SQLException {
	        User user = userDAO.getUserById(id);
	        if (user == null) {
	            throw new IllegalArgumentException("User not found");
	        }
	        return user;
	    }
	    
	    /**
	     * Get all users
	     */
	    public List<User> getAllUsers() throws SQLException {
	        return userDAO.getAllUsers();
	    }
	    
	    /**
	     * Update a user
	     */
	    public boolean updateUser(User user) throws SQLException {
	        // Check if user exists
	        User existingUser = userDAO.getUserById(user.getId());
	        if (existingUser == null) {
	            throw new IllegalArgumentException("User not found");
	        }
	        
	        // Check if email already exists (if email is being changed)
	        if (!existingUser.getEmail().equals(user.getEmail())) {
	            User userWithEmail = userDAO.getUserByEmail(user.getEmail());
	            if (userWithEmail != null) {
	                throw new IllegalArgumentException("Email already exists");
	            }
	        }
	        
	        return userDAO.updateUser(user);
	    }
	    
	    /**
	     * Delete a user
	     */
	    public boolean deleteUser(int id) throws SQLException {
	        // Check if user exists
	        User existingUser = userDAO.getUserById(id);
	        if (existingUser == null) {
	            throw new IllegalArgumentException("User not found");
	        }
	        
	        return userDAO.deleteUser(id);
	    }
	

}
