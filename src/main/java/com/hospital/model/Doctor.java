package com.hospital.model;

public class Doctor 
{
	

	/**
	 * Doctor entity class representing doctors in the system
	 */
	
	    private int id;
	    private String name;
	    private String specialization;
	    private String availability;
	    private String contact;
	    private int userId; // Foreign key to User table
	    
	    // Default constructor
	    public Doctor() {
	    }
	    
	    // Parameterized constructor
	    public Doctor(int id, String name, String specialization, String availability, String contact, int userId) {
	        this.id = id;
	        this.name = name;
	        this.specialization = specialization;
	        this.availability = availability;
	        this.contact = contact;
	        this.userId = userId;
	    }
	    
	    // Constructor without ID for new doctor creation
	    public Doctor(String name, String specialization, String availability, String contact, int userId) {
	        this.name = name;
	        this.specialization = specialization;
	        this.availability = availability;
	        this.contact = contact;
	        this.userId = userId;
	    }
	    
	    // Getters and Setters
	    public int getId() {
	        return id;
	    }
	    
	    public void setId(int id) {
	        this.id = id;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getSpecialization() {
	        return specialization;
	    }
	    
	    public void setSpecialization(String specialization) {
	        this.specialization = specialization;
	    }
	    
	    public String getAvailability() {
	        return availability;
	    }
	    
	    public void setAvailability(String availability) {
	        this.availability = availability;
	    }
	    
	    public String getContact() {
	        return contact;
	    }
	    
	    public void setContact(String contact) {
	        this.contact = contact;
	    }
	    
	    public int getUserId() {
	        return userId;
	    }
	    
	    public void setUserId(int userId) {
	        this.userId = userId;
	    }
	    
	    @Override
	    public String toString() {
	        return "Doctor{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", specialization='" + specialization + '\'' +
	                ", availability='" + availability + '\'' +
	                ", contact='" + contact + '\'' +
	                ", userId=" + userId +
	                '}';
	    }

}
