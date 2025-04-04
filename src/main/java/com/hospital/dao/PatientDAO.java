package com.hospital.dao;

import com.hospital.model.Patient;
import com.hospital.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PatientDAO
{
	

	

	/**
	 * Data Access Object for Patient entity
	 */
	
	    private Connection connection;
	    
	    public PatientDAO() {
	        this.connection = DatabaseConnection.getConnection();
	    }
	    
	    /**
	     * Add a new patient to the database
	     */
	    public int addPatient(Patient patient) throws SQLException {
	        String query = "INSERT INTO patients (name, age, gender, contact, medical_history, user_id) VALUES (?, ?, ?, ?, ?, ?)";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, patient.getName());
	            statement.setInt(2, patient.getAge());
	            statement.setString(3, patient.getGender());
	            statement.setString(4, patient.getContact());
	            statement.setString(5, patient.getMedicalHistory());
	            statement.setInt(6, patient.getUserId());
	            
	            int affectedRows = statement.executeUpdate();
	            
	            if (affectedRows == 0) {
	                throw new SQLException("Creating patient failed, no rows affected.");
	            }
	            
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating patient failed, no ID obtained.");
	                }
	            }
	        }
	    }
	    
	    /**
	     * Get a patient by ID
	     */
	    public Patient getPatientById(int id) throws SQLException {
	        String query = "SELECT * FROM patients WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractPatientFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get a patient by user ID
	     */
	    public Patient getPatientByUserId(int userId) throws SQLException {
	        String query = "SELECT * FROM patients WHERE user_id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, userId);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractPatientFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get all patients
	     */
	    public List<Patient> getAllPatients() throws SQLException {
	        List<Patient> patients = new ArrayList<>();
	        String query = "SELECT * FROM patients";
	        
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {
	            
	            while (resultSet.next()) {
	                patients.add(extractPatientFromResultSet(resultSet));
	            }
	        }
	        
	        return patients;
	    }
	    
	    /**
	     * Update a patient
	     */
	    public boolean updatePatient(Patient patient) throws SQLException {
	        String query = "UPDATE patients SET name = ?, age = ?, gender = ?, contact = ?, medical_history = ? WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, patient.getName());
	            statement.setInt(2, patient.getAge());
	            statement.setString(3, patient.getGender());
	            statement.setString(4, patient.getContact());
	            statement.setString(5, patient.getMedicalHistory());
	            statement.setInt(6, patient.getId());
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Delete a patient
	     */
	    public boolean deletePatient(int id) throws SQLException {
	        String query = "DELETE FROM patients WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Helper method to extract a Patient from a ResultSet
	     */
	    private Patient extractPatientFromResultSet(ResultSet resultSet) throws SQLException {
	        Patient patient = new Patient();
	        patient.setId(resultSet.getInt("id"));
	        patient.setName(resultSet.getString("name"));
	        patient.setAge(resultSet.getInt("age"));
	        patient.setGender(resultSet.getString("gender"));
	        patient.setContact(resultSet.getString("contact"));
	        patient.setMedicalHistory(resultSet.getString("medical_history"));
	        patient.setUserId(resultSet.getInt("user_id"));
	        return patient;
	    }
	

}
