package com.hospital.service;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.UserDAO;
import com.hospital.model.Doctor;
import com.hospital.model.User;

import java.sql.SQLException;
import java.util.List;
public class DoctorService
{

	/**
	 * Service class for Doctor operations
	 */
	
	    private DoctorDAO doctorDAO;
	    private UserDAO userDAO;
	    
	    public DoctorService() {
	        this.doctorDAO = new DoctorDAO();
	        this.userDAO = new UserDAO();
	    }
	    
	    /**
	     * Register a new doctor with user account
	     */
	    public int registerDoctor(Doctor doctor, User user) throws SQLException {
	        // Set user role to DOCTOR
	        user.setRole("DOCTOR");
	        
	        // Register user first
	        UserService userService = new UserService();
	        int userId = userService.registerUser(user);
	        
	        // Set user ID for doctor
	        doctor.setUserId(userId);
	        
	        // Add doctor to database
	        return doctorDAO.addDoctor(doctor);
	    }
	    
	    /**
	     * Get a doctor by ID
	     */
	    public Doctor getDoctorById(int id) throws SQLException {
	        Doctor doctor = doctorDAO.getDoctorById(id);
	        if (doctor == null) {
	            throw new IllegalArgumentException("Doctor not found");
	        }
	        return doctor;
	    }
	    
	    /**
	     * Get a doctor by user ID
	     */
	    public Doctor getDoctorByUserId(int userId) throws SQLException {
	        Doctor doctor = doctorDAO.getDoctorByUserId(userId);
	        if (doctor == null) {
	            throw new IllegalArgumentException("Doctor not found for this user");
	        }
	        return doctor;
	    }
	    
	    /**
	     * Get all doctors
	     */
	    public List<Doctor> getAllDoctors() throws SQLException {
	        return doctorDAO.getAllDoctors();
	    }
	    
	    /**
	     * Get doctors by specialization
	     */
	    public List<Doctor> getDoctorsBySpecialization(String specialization) throws SQLException {
	        return doctorDAO.getDoctorsBySpecialization(specialization);
	    }
	    
	    /**
	     * Update a doctor
	     */
	    public boolean updateDoctor(Doctor doctor) throws SQLException {
	        // Check if doctor exists
	        Doctor existingDoctor = doctorDAO.getDoctorById(doctor.getId());
	        if (existingDoctor == null) {
	            throw new IllegalArgumentException("Doctor not found");
	        }
	        
	        return doctorDAO.updateDoctor(doctor);
	    }
	    
	    /**
	     * Delete a doctor
	     */
	    public boolean deleteDoctor(int id) throws SQLException {
	        // Check if doctor exists
	        Doctor existingDoctor = doctorDAO.getDoctorById(id);
	        if (existingDoctor == null) {
	            throw new IllegalArgumentException("Doctor not found");
	        }
	        
	        // Delete the doctor
	        boolean doctorDeleted = doctorDAO.deleteDoctor(id);
	        
	        // Delete the associated user
	        if (doctorDeleted) {
	            UserService userService = new UserService();
	            userService.deleteUser(existingDoctor.getUserId());
	        }
	        
	        return doctorDeleted;
	    }
	

}
