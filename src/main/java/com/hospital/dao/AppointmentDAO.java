package com.hospital.dao;

import com.hospital.model.Appointment;
import com.hospital.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO
{
	
	

	/**
	 * Data Access Object for Appointment entity
	 */
	
	    private Connection connection;
	    
	    public AppointmentDAO() {
	        this.connection = DatabaseConnection.getConnection();
	    }
	    
	    /**
	     * Add a new appointment to the database
	     */
	    public int addAppointment(Appointment appointment) throws SQLException {
	        String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, ?)";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setInt(1, appointment.getPatientId());
	            statement.setInt(2, appointment.getDoctorId());
	            statement.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
	            statement.setString(4, appointment.getStatus());
	            
	            int affectedRows = statement.executeUpdate();
	            
	            if (affectedRows == 0) {
	                throw new SQLException("Creating appointment failed, no rows affected.");
	            }
	            
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating appointment failed, no ID obtained.");
	                }
	            }
	        }
	    }
	    
	    /**
	     * Get an appointment by ID
	     */
	    public Appointment getAppointmentById(int id) throws SQLException {
	        String query = "SELECT * FROM appointments WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractAppointmentFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get appointments by patient ID
	     */
	    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
	        List<Appointment> appointments = new ArrayList<>();
	        String query = "SELECT * FROM appointments WHERE patient_id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, patientId);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    appointments.add(extractAppointmentFromResultSet(resultSet));
	                }
	            }
	        }
	        
	        return appointments;
	    }
	    
	    /**
	     * Get appointments by doctor ID
	     */
	    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws SQLException {
	        List<Appointment> appointments = new ArrayList<>();
	        String query = "SELECT * FROM appointments WHERE doctor_id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, doctorId);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    appointments.add(extractAppointmentFromResultSet(resultSet));
	                }
	            }
	        }
	        
	        return appointments;
	    }
	    
	    /**
	     * Get all appointments
	     */
	    public List<Appointment> getAllAppointments() throws SQLException {
	        List<Appointment> appointments = new ArrayList<>();
	        String query = "SELECT * FROM appointments";
	        
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {
	            
	            while (resultSet.next()) {
	                appointments.add(extractAppointmentFromResultSet(resultSet));
	            }
	        }
	        
	        return appointments;
	    }
	    
	    /**
	     * Update an appointment
	     */
	    public boolean updateAppointment(Appointment appointment) throws SQLException {
	        String query = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, status = ? WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, appointment.getPatientId());
	            statement.setInt(2, appointment.getDoctorId());
	            statement.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
	            statement.setString(4, appointment.getStatus());
	            statement.setInt(5, appointment.getId());
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Delete an appointment
	     */
	    public boolean deleteAppointment(int id) throws SQLException {
	        String query = "DELETE FROM appointments WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Helper method to extract an Appointment from a ResultSet
	     */
	    private Appointment extractAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
	        Appointment appointment = new Appointment();
	        appointment.setId(resultSet.getInt("id"));
	        appointment.setPatientId(resultSet.getInt("patient_id"));
	        appointment.setDoctorId(resultSet.getInt("doctor_id"));
	        appointment.setAppointmentDate(resultSet.getTimestamp("appointment_date").toLocalDateTime());
	        appointment.setStatus(resultSet.getString("status"));
	        return appointment;
	    }
	

}
