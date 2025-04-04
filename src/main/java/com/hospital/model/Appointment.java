package com.hospital.model;
import java.time.LocalDateTime;

public class Appointment
{

	/**
	 * Appointment entity class representing appointments in the system
	 */
	
	    private int id;
	    private int patientId;
	    private int doctorId;
	    private LocalDateTime appointmentDate;
	    private String status; // SCHEDULED, COMPLETED, CANCELLED
	    
	    // Default constructor
	    public Appointment() {
	    }
	    
	    // Parameterized constructor
	    public Appointment(int id, int patientId, int doctorId, LocalDateTime appointmentDate, String status) {
	        this.id = id;
	        this.patientId = patientId;
	        this.doctorId = doctorId;
	        this.appointmentDate = appointmentDate;
	        this.status = status;
	    }
	    
	    // Constructor without ID for new appointment creation
	    public Appointment(int patientId, int doctorId, LocalDateTime appointmentDate, String status) {
	        this.patientId = patientId;
	        this.doctorId = doctorId;
	        this.appointmentDate = appointmentDate;
	        this.status = status;
	    }
	    
	    // Getters and Setters
	    public int getId() {
	        return id;
	    }
	    
	    public void setId(int id) {
	        this.id = id;
	    }
	    
	    public int getPatientId() {
	        return patientId;
	    }
	    
	    public void setPatientId(int patientId) {
	        this.patientId = patientId;
	    }
	    
	    public int getDoctorId() {
	        return doctorId;
	    }
	    
	    public void setDoctorId(int doctorId) {
	        this.doctorId = doctorId;
	    }
	    
	    public LocalDateTime getAppointmentDate() {
	        return appointmentDate;
	    }
	    
	    public void setAppointmentDate(LocalDateTime appointmentDate) {
	        this.appointmentDate = appointmentDate;
	    }
	    
	    public String getStatus() {
	        return status;
	    }
	    
	    public void setStatus(String status) {
	        this.status = status;
	    }
	    
	    @Override
	    public String toString() {
	        return "Appointment{" +
	                "id=" + id +
	                ", patientId=" + patientId +
	                ", doctorId=" + doctorId +
	                ", appointmentDate=" + appointmentDate +
	                ", status='" + status + '\'' +
	                '}';
	    }
	

}
