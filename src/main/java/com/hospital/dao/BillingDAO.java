package com.hospital.dao;

import com.hospital.model.Billing;
import com.hospital.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BillingDAO 
{
	

	

	/**
	 * Data Access Object for Billing entity
	 */
	
	    private Connection connection;
	    
	    public BillingDAO() {
	        this.connection = DatabaseConnection.getConnection();
	    }
	    
	    /**
	     * Add a new billing record to the database
	     */
	    public int addBilling(Billing billing) throws SQLException {
	        String query = "INSERT INTO billing (patient_id, amount, payment_status, billing_date) VALUES (?, ?, ?, ?)";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setInt(1, billing.getPatientId());
	            statement.setDouble(2, billing.getAmount());
	            statement.setString(3, billing.getPaymentStatus());
	            statement.setTimestamp(4, Timestamp.valueOf(billing.getBillingDate()));
	            
	            int affectedRows = statement.executeUpdate();
	            
	            if (affectedRows == 0) {
	                throw new SQLException("Creating billing record failed, no rows affected.");
	            }
	            
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating billing record failed, no ID obtained.");
	                }
	            }
	        }
	    }
	    
	    /**
	     * Get a billing record by ID
	     */
	    public Billing getBillingById(int id) throws SQLException {
	        String query = "SELECT * FROM billing WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return extractBillingFromResultSet(resultSet);
	                }
	            }
	        }
	        
	        return null;
	    }
	    
	    /**
	     * Get billing records by patient ID
	     */
	    public List<Billing> getBillingsByPatientId(int patientId) throws SQLException {
	        List<Billing> billings = new ArrayList<>();
	        String query = "SELECT * FROM billing WHERE patient_id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, patientId);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    billings.add(extractBillingFromResultSet(resultSet));
	                }
	            }
	        }
	        
	        return billings;
	    }
	    
	    /**
	     * Get all billing records
	     */
	    public List<Billing> getAllBillings() throws SQLException {
	        List<Billing> billings = new ArrayList<>();
	        String query = "SELECT * FROM billing";
	        
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {
	            
	            while (resultSet.next()) {
	                billings.add(extractBillingFromResultSet(resultSet));
	            }
	        }
	        
	        return billings;
	    }
	    
	    /**
	     * Update a billing record
	     */
	    public boolean updateBilling(Billing billing) throws SQLException {
	        String query = "UPDATE billing SET patient_id = ?, amount = ?, payment_status = ?, billing_date = ? WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, billing.getPatientId());
	            statement.setDouble(2, billing.getAmount());
	            statement.setString(3, billing.getPaymentStatus());
	            statement.setTimestamp(4, Timestamp.valueOf(billing.getBillingDate()));
	            statement.setInt(5, billing.getId());
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Delete a billing record
	     */
	    public boolean deleteBilling(int id) throws SQLException {
	        String query = "DELETE FROM billing WHERE id = ?";
	        
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, id);
	            
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    }
	    
	    /**
	     * Helper method to extract a Billing from a ResultSet
	     */
	    private Billing extractBillingFromResultSet(ResultSet resultSet) throws SQLException {
	        Billing billing = new Billing();
	        billing.setId(resultSet.getInt("id"));
	        billing.setPatientId(resultSet.getInt("patient_id"));
	        billing.setAmount(resultSet.getDouble("amount"));
	        billing.setPaymentStatus(resultSet.getString("payment_status"));
	        billing.setBillingDate(resultSet.getTimestamp("billing_date").toLocalDateTime());
	        return billing;
	    }
	

}
