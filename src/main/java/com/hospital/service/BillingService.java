package com.hospital.service;

import com.hospital.dao.BillingDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.model.Billing;
import com.hospital.model.Patient;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
public class BillingService
{
	

	/**
	 * Service class for Billing operations
	 */
	
	    private BillingDAO billingDAO;
	    private PatientDAO patientDAO;
	    
	    public BillingService() {
	        this.billingDAO = new BillingDAO();
	        this.patientDAO = new PatientDAO();
	    }
	    
	    /**
	     * Generate a new billing record
	     */
	    public int generateBill(Billing billing) throws SQLException {
	        // Validate patient
	        Patient patient = patientDAO.getPatientById(billing.getPatientId());
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        // Validate amount
	        if (billing.getAmount() <= 0) {
	            throw new IllegalArgumentException("Amount must be greater than zero");
	        }
	        
	        // Set initial payment status to PENDING
	        billing.setPaymentStatus("PENDING");
	        
	        // Set billing date to current date and time
	        billing.setBillingDate(LocalDateTime.now());
	        
	        // Add billing record to database
	        return billingDAO.addBilling(billing);
	    }
	    
	    /**
	     * Get a billing record by ID
	     */
	    public Billing getBillingById(int id) throws SQLException {
	        Billing billing = billingDAO.getBillingById(id);
	        if (billing == null) {
	            throw new IllegalArgumentException("Billing record not found");
	        }
	        return billing;
	    }
	    
	    /**
	     * Get billing records by patient ID
	     */
	    public List<Billing> getBillingsByPatientId(int patientId) throws SQLException {
	        // Validate patient
	        Patient patient = patientDAO.getPatientById(patientId);
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        return billingDAO.getBillingsByPatientId(patientId);
	    }
	    
	    /**
	     * Get all billing records
	     */
	    public List<Billing> getAllBillings() throws SQLException {
	        return billingDAO.getAllBillings();
	    }
	    
	    /**
	     * Update a billing record
	     */
	    public boolean updateBilling(Billing billing) throws SQLException {
	        // Check if billing record exists
	        Billing existingBilling = billingDAO.getBillingById(billing.getId());
	        if (existingBilling == null) {
	            throw new IllegalArgumentException("Billing record not found");
	        }
	        
	        // Validate patient
	        Patient patient = patientDAO.getPatientById(billing.getPatientId());
	        if (patient == null) {
	            throw new IllegalArgumentException("Patient not found");
	        }
	        
	        // Validate amount
	        if (billing.getAmount() <= 0) {
	            throw new IllegalArgumentException("Amount must be greater than zero");
	        }
	        
	        return billingDAO.updateBilling(billing);
	    }
	    
	    /**
	     * Mark a billing record as paid
	     */
	    public boolean markAsPaid(int id) throws SQLException {
	        // Check if billing record exists
	        Billing existingBilling = billingDAO.getBillingById(id);
	        if (existingBilling == null) {
	            throw new IllegalArgumentException("Billing record not found");
	        }
	        
	        // Set payment status to PAID
	        existingBilling.setPaymentStatus("PAID");
	        
	        return billingDAO.updateBilling(existingBilling);
	    }
	    
	    /**
	     * Cancel a billing record
	     */
	    public boolean cancelBilling(int id) throws SQLException {
	        // Check if billing record exists
	        Billing existingBilling = billingDAO.getBillingById(id);
	        if (existingBilling == null) {
	            throw new IllegalArgumentException("Billing record not found");
	        }
	        
	        // Set payment status to CANCELLED
	        existingBilling.setPaymentStatus("CANCELLED");
	        
	        return billingDAO.updateBilling(existingBilling);
	    }
	    
	    /**
	     * Delete a billing record
	     */
	    public boolean deleteBilling(int id) throws SQLException {
	        // Check if billing record exists
	        Billing existingBilling = billingDAO.getBillingById(id);
	        if (existingBilling == null) {
	            throw new IllegalArgumentException("Billing record not found");
	        }
	        
	        return billingDAO.deleteBilling(id);
	    }
	

}
