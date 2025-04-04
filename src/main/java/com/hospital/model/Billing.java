package com.hospital.model;
import java.time.LocalDateTime;
public class Billing
{

	/**
	 * Billing entity class representing billing records in the system
	 */
	
	    private int id;
	    private int patientId;
	    private double amount;
	    private String paymentStatus; // PENDING, PAID, CANCELLED
	    private LocalDateTime billingDate;
	    
	    // Default constructor
	    public Billing() {
	    }
	    
	    // Parameterized constructor
	    public Billing(int id, int patientId, double amount, String paymentStatus, LocalDateTime billingDate) {
	        this.id = id;
	        this.patientId = patientId;
	        this.amount = amount;
	        this.paymentStatus = paymentStatus;
	        this.billingDate = billingDate;
	    }
	    
	    // Constructor without ID for new billing creation
	    public Billing(int patientId, double amount, String paymentStatus, LocalDateTime billingDate) {
	        this.patientId = patientId;
	        this.amount = amount;
	        this.paymentStatus = paymentStatus;
	        this.billingDate = billingDate;
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
	    
	    public double getAmount() {
	        return amount;
	    }
	    
	    public void setAmount(double amount) {
	        this.amount = amount;
	    }
	    
	    public String getPaymentStatus() {
	        return paymentStatus;
	    }
	    
	    public void setPaymentStatus(String paymentStatus) {
	        this.paymentStatus = paymentStatus;
	    }
	    
	    public LocalDateTime getBillingDate() {
	        return billingDate;
	    }
	    
	    public void setBillingDate(LocalDateTime billingDate) {
	        this.billingDate = billingDate;
	    }
	    
	    @Override
	    public String toString() {
	        return "Billing{" +
	                "id=" + id +
	                ", patientId=" + patientId +
	                ", amount=" + amount +
	                ", paymentStatus='" + paymentStatus + '\'' +
	                ", billingDate=" + billingDate +
	                '}';
	    }
	
}
