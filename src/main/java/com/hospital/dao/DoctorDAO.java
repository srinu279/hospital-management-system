package com.hospital.dao;

import com.hospital.model.Doctor;
import com.hospital.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DoctorDAO 
{
	

	

	/**
	 * Data Access Object for Doctor entity
	 */
	
	    private Connection connection;
	    
	    public DoctorDAO() {
	        this.connection = DatabaseConnection.getConnection();
	    }
	    
	    /**
	     * Add a new doctor to the database
	     */
	    public int addDoctor(Doctor doctor) throws SQLException {
	        String query = "INSERT INTO doctors (name, specialization, availability, contact, user_id) VALUES (?, ?, ?, ?, ?)";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, doctor.getName());
	            statement.setString(2, doctor.getSpecialization());
	            statement.setString(3, doctor.getAvailability());
	            statement.setString(4, doctor.getContact());
	            statement.setInt(5, doctor.getUserId());
	            
	            int affectedRows = statement.executeUpdate();
	            
	            if (affectedRows == 0) {
	                throw new SQLException("Creating doctor failed, no rows affected.");
	            }
	            
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating doctor failed, no ID obtained.");
	                }
	            }
	        }
	    }
	    
	    /**
	     * Get a doctor by ID
	     */
	    public Doctor getDoctorById(int id) throws SQLException {
	        String query = "SELECT * FROM doctors WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractDoctorFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get a doctor by user ID
	     */
	    public Doctor getDoctorByUserId(int userId) throws SQLException {
	        String query = "SELECT * FROM doctors WHERE user_id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, userId);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractDoctorFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get all doctors
	     */
	    public List<Doctor> getAllDoctors() throws SQLException {
	        List<Doctor> doctors = new ArrayList<>();
	        String query = "SELECT * FROM doctors";
	        
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {
	            
	            while (resultSet.next()) {
	                doctors.add(extractDoctorFromResultSet(resultSet));
	            }
	        }
	        
	        return doctors;
	    }
	    
	    /**
	     * Get doctors by specialization
	     */
	    public List<Doctor> getDoctorsBySpecialization(String specialization) throws SQLException {
	        List<Doctor> doctors = new ArrayList<>();
	        String query = "SELECT * FROM doctors WHERE specialization = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, specialization);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    doctors.add(extractDoctorFromResultSet(resultSet));
	                }
	            }
	        }
	        
	        return doctors;
	    }
	    
	    /**
	     * Update a doctor
	     */
	    public boolean updateDoctor(Doctor doctor) throws SQLException {
	        String query = "UPDATE doctors SET name = ?, specialization = ?, availability = ?, contact = ? WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, doctor.getName());
	            statement.setString(2, doctor.getSpecialization());
	            statement.setString(3, doctor.getAvailability());
	            statement.setString(4, doctor.getContact());
	            statement.setInt(5, doctor.getId());
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Delete a doctor
	     */
	    public boolean deleteDoctor(int id) throws SQLException {
	        String query = "DELETE FROM doctors WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Helper method to extract a Doctor from a ResultSet
	     */
	    private Doctor extractDoctorFromResultSet(ResultSet resultSet) throws SQLException {
	        Doctor doctor = new Doctor();
	        doctor.setId(resultSet.getInt("id"));
	        doctor.setName(resultSet.getString("name"));
	        doctor.setSpecialization(resultSet.getString("specialization"));
	        doctor.setAvailability(resultSet.getString("availability"));
	        doctor.setContact(resultSet.getString("contact"));
	        doctor.setUserId(resultSet.getInt("user_id"));
	        return doctor;
	    }
	
}
