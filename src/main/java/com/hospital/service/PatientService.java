package com.hospital.service;

import com.hospital.dao.PatientDAO;
import com.hospital.dao.UserDAO;
import com.hospital.model.Patient;
import com.hospital.model.User;

import java.sql.SQLException;
import java.util.List;
public class PatientService
{
	


	/**
	 * Service class for Patient operations
	 */
	
	    private PatientDAO patientDAO;
	    private UserDAO userDAO;
	    
	    public PatientService() {
	        this.patientDAO = new PatientDAO();
	        this.userDAO = new UserDAO();
	    }
	    
	    /**
	     * Register a new patient with user account
	     */
	    public int registerPatient(Patient patient, User user) throws SQLException {
	        // Set user role to PATIENT
	        user.setRole("PATIENT");
	        
	        // Register user first
	        UserService userService = new UserService();
	        int userId = userService.registerUser(user);
	        
	        // Set user ID for patient
	        patient.setUserId(userId);
	        
	        // Add patient to database
	        return patientDAO.addPatient(patient);
	    }
	    
	    /**
	     * Get a patient by ID
	     */
	    public Patient getPatientById(int id) throws SQLException {
	        Patient patient = patientDAO.getPatientById(id);
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        return patient;
	    }
	    
	    /**
	     * Get a patient by user ID
	     */
	    public Patient getPatientByUserId(int userId) throws SQLException {
	        Patient patient = patientDAO.getPatientByUserId(userId);
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found for this user");
	        }
	        return patient;
	    }
	    
	    /**
	     * Get all patients
	     */
	    public List<Patient> getAllPatients() throws SQLException {
	        return patientDAO.getAllPatients();
	    }
	    
	    /**
	     * Update a patient
	     */
	    public boolean updatePatient(Patient patient) throws SQLException {
	        // Check if patient exists
	        Patient existingPatient = patientDAO.getPatientById(patient.getId());
	        if (existingPatient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        return patientDAO.updatePatient(patient);
	    }
	    
	    /**
	     * Delete a patient
	     */
	    public boolean deletePatient(int id) throws SQLException {
	        // Check if patient exists
	        Patient existingPatient = patientDAO.getPatientById(id);
	        if (existingPatient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        // Delete the patient
	        boolean patientDeleted = patientDAO.deletePatient(id);
	        
	        // Delete the associated user
	        if (patientDeleted) {
	            UserService userService = new UserService();
	            userService.deleteUser(existingPatient.getUserId());
	        }
	        
	        return patientDeleted;
	    }
	
}
