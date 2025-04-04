package com.hospital.model;

public class Patient
{
	/**
	 * Patient entity class representing patients in the system
	 */
	
	    private int id;
	    private String name;
	    private int age;
	    private String gender;
	    private String contact;
	    private String medicalHistory;
	    private int userId; // Foreign key to User table
	    
	    // Default constructor
	    public Patient() {
	    }
	    
	    // Parameterized constructor
	    public Patient(int id, String name, int age, String gender, String contact, String medicalHistory, int userId) {
	        this.id = id;
	        this.name = name;
	        this.age = age;
	        this.gender = gender;
	        this.contact = contact;
	        this.medicalHistory = medicalHistory;
	        this.userId = userId;
	    }
	    
	    // Constructor without ID for new patient creation
	    public Patient(String name, int age, String gender, String contact, String medicalHistory, int userId) {
	        this.name = name;
	        this.age = age;
	        this.gender = gender;
	        this.contact = contact;
	        this.medicalHistory = medicalHistory;
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
	    
	    public int getAge() {
	        return age;
	    }
	    
	    public void setAge(int age) {
	        this.age = age;
	    }
	    
	    public String getGender() {
	        return gender;
	    }
	    
	    public void setGender(String gender) {
	        this.gender = gender;
	    }
	    
	    public String getContact() {
	        return contact;
	    }
	    
	    public void setContact(String contact) {
	        this.contact = contact;
	    }
	    
	    public String getMedicalHistory() {
	        return medicalHistory;
	    }
	    
	    public void setMedicalHistory(String medicalHistory) {
	        this.medicalHistory = medicalHistory;
	    }
	    
	    public int getUserId() {
	        return userId;
	    }
	    
	    public void setUserId(int userId) {
	        this.userId = userId;
	    }
	    
	    @Override
	    public String toString() {
	        return "Patient{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", age=" + age +
	                ", gender='" + gender + '\'' +
	                ", contact='" + contact + '\'' +
	                ", medicalHistory='" + medicalHistory + '\'' +
	                ", userId=" + userId +
	                '}';
	    }

}
