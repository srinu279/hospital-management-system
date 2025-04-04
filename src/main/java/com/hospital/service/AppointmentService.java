package com.hospital.service;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService
{

	/**
	 * Service class for Appointment operations
	 */
	
	    private AppointmentDAO appointmentDAO;
	    private PatientDAO patientDAO;
	    private DoctorDAO doctorDAO;
	    
	    public AppointmentService() {
	        this.appointmentDAO = new AppointmentDAO();
	        this.patientDAO = new PatientDAO();
	        this.doctorDAO = new DoctorDAO();
	    }
	    
	    /**
	     * Schedule a new appointment
	     */
	    public int scheduleAppointment(Appointment appointment) throws SQLException {
	        // Validate patient
	        Patient patient = patientDAO.getPatientById(appointment.getPatientId());
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        // Validate doctor
	        Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
	        if (doctor == null) {
	            throw new IllegalArgumentException("Doctor not found");
	        }
	        
	        // Validate appointment date (not in the past)
	        if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
	            throw new IllegalArgumentException("Appointment date cannot be in the past");
	        }
	        
	        // Set initial status to SCHEDULED
	        appointment.setStatus("SCHEDULED");
	        
	        // Add appointment to database
	        return appointmentDAO.addAppointment(appointment);
	    }
	    
	    /**
	     * Get an appointment by ID
	     */
	    public Appointment getAppointmentById(int id) throws SQLException {
	        Appointment appointment = appointmentDAO.getAppointmentById(id);
	        if (appointment == null) {
	            throw new IllegalArgumentException("Appointment not found");
	        }
	        return appointment;
	    }
	    
	    /**
	     * Get appointments by patient ID
	     */
	    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
	        // Validate patient
	        Patient patient = patientDAO.getPatientById(patientId);
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        return appointmentDAO.getAppointmentsByPatientId(patientId);
	    }
	    
	    /**
	     * Get appointments by doctor ID
	     */
	    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws SQLException {
	        // Validate doctor
	        Doctor doctor = doctorDAO.getDoctorById(doctorId);
	        if (doctor == null) {
	            throw new IllegalArgumentException("Doctor not found");
	        }
	        
	        return appointmentDAO.getAppointmentsByDoctorId(doctorId);
	    }
	    
	    /**
	     * Get all appointments
	     */
	    public List<Appointment> getAllAppointments() throws SQLException {
	        return appointmentDAO.getAllAppointments();
	    }
	    
	    /**
	     * Update an appointment
	     */
	    public boolean updateAppointment(Appointment appointment) throws SQLException {
	        // Check if appointment exists
	        Appointment existingAppointment = appointmentDAO.getAppointmentById(appointment.getId());
	        if (existingAppointment == null) {
	            throw new IllegalArgumentException("Appointment not found");
	        }
	        
	        // Validate patient
	        Patient patient = patientDAO.getPatientById(appointment.getPatientId());
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        // Validate doctor
	        Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
	        if (doctor == null) {
	            throw new IllegalArgumentException("Doctor not found");
	        }
	        
	        // Validate appointment date (not in the past)
	        if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
	            throw new IllegalArgumentException("Appointment date cannot be in the past");
	        }
	        
	        return appointmentDAO.updateAppointment(appointment);
	    }
	    
	    /**
	     * Cancel an appointment
	     */
	    public boolean cancelAppointment(int id) throws SQLException {
	        // Check if appointment exists
	        Appointment existingAppointment = appointmentDAO.getAppointmentById(id);
	        if (existingAppointment == null) {
	            throw new IllegalArgumentException("Appointment not found");
	        }
	        
	        // Set status to CANCELLED
	        existingAppointment.setStatus("CANCELLED");
	        
	        return appointmentDAO.updateAppointment(existingAppointment);
	    }
	    
	    /**
	     * Complete an appointment
	     */
	    public boolean completeAppointment(int id) throws SQLException {
	        // Check if appointment exists
	        Appointment existingAppointment = appointmentDAO.getAppointmentById(id);
	        if (existingAppointment == null) {
	            throw new IllegalArgumentException("Appointment not found");
	        }
	        
	        // Set status to COMPLETED
	        existingAppointment.setStatus("COMPLETED");
	        
	        return appointmentDAO.updateAppointment(existingAppointment);
	    }
	    
	    /**
	     * Delete an appointment
	     */
	    public boolean deleteAppointment(int id) throws SQLException {
	        // Check if appointment exists
	        Appointment existingAppointment = appointmentDAO.getAppointmentById(id);
	        if (existingAppointment == null) {
	            throw new IllegalArgumentException("Appointment not found");
	        }
	        
	        return appointmentDAO.deleteAppointment(id);
	    }
	

}
