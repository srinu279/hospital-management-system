package com.hospital1;

import com.hospital.model.*;
import com.hospital.service.*;
import com.hospital.util.DatabaseConnection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class HospitalManagementSystem
{
	/**
	 * Main class for Hospital Management System
	 */
	
	    private static Scanner scanner = new Scanner(System.in);
	    private static UserService userService = new UserService();
	    private static PatientService patientService = new PatientService();
	    private static DoctorService doctorService = new DoctorService();
	    private static AppointmentService appointmentService = new AppointmentService();
	    private static BillingService billingService = new BillingService();
	    
	    private static User currentUser = null;
	    
	    public static void main(String[] args) {
	        try {
	            // Initialize database connection
	            DatabaseConnection.getConnection();
	            
	            // Start the application
	            showMainMenu();
	            
	            // Close database connection
	            DatabaseConnection.closeConnection();
	        } catch (Exception e) {
	            System.err.println("An error occurred: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            scanner.close();
	        }
	    }
	    
	    /**
	     * Display the main menu
	     */
	    private static void showMainMenu() {
	        boolean exit = false;
	        
	        while (!exit) {
	            System.out.println("\n===== Hospital Management System =====");
	            System.out.println("1. Login");
	            System.out.println("2. Register as Patient");
	            System.out.println("3. Exit");
	            System.out.print("Enter your choice: ");
	            
	            int choice = getIntInput();
	            
	            switch (choice) {
	                case 1:
	                    login();
	                    break;
	                case 2:
	                    registerPatient();
	                    break;
	                case 3:
	                    exit = true;
	                    System.out.println("Thank you for using Hospital Management System!");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	    
	    /**
	     * Login functionality
	     */
	    private static void login() {
	        System.out.println("\n===== Login =====");
	        System.out.print("Enter email: ");
	        String email = scanner.nextLine();
	        System.out.print("Enter password: ");
	        String password = scanner.nextLine();
	        
	        try {
	            currentUser = userService.login(email, password);
	            System.out.println("Login successful! Welcome, " + currentUser.getName() + "!");
	            
	            // Show appropriate dashboard based on user role
	            switch (currentUser.getRole()) {
	                case "ADMIN":
	                    showAdminDashboard();
	                    break;
	                case "DOCTOR":
	                    showDoctorDashboard();
	                    break;
	                case "PATIENT":
	                    showPatientDashboard();
	                    break;
	                default:
	                    System.out.println("Invalid role. Please contact administrator.");
	            }
	        } catch (Exception e) {
	            System.out.println("Login failed: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Register a new patient
	     */
	    private static void registerPatient() {
	        System.out.println("\n===== Patient Registration =====");
	        
	        // Get user details
	        System.out.print("Enter name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter email: ");
	        String email = scanner.nextLine();
	        System.out.print("Enter password: ");
	        String password = scanner.nextLine();
	        
	        // Get patient details
	        System.out.print("Enter age: ");
	        int age = getIntInput();
	        System.out.print("Enter gender (M/F): ");
	        String gender = scanner.nextLine();
	        System.out.print("Enter contact number: ");
	        String contact = scanner.nextLine();
	        System.out.print("Enter medical history: ");
	        String medicalHistory = scanner.nextLine();
	        
	        try {
	            // Create user and patient objects
	            User user = new User(name, email, password, "PATIENT");
	            Patient patient = new Patient(name, age, gender, contact, medicalHistory, 0);
	            
	            // Register patient
	            int patientId = patientService.registerPatient(patient, user);
	            System.out.println("Patient registered successfully with ID: " + patientId);
	        } catch (Exception e) {
	            System.out.println("Registration failed: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Show admin dashboard
	     */
	    private static void showAdminDashboard() {
	        boolean logout = false;
	        
	        while (!logout) {
	            System.out.println("\n===== Admin Dashboard =====");
	            System.out.println("1. Manage Doctors");
	            System.out.println("2. Manage Patients");
	            System.out.println("3. Manage Appointments");
	            System.out.println("4. Manage Billing");
	            System.out.println("5. Logout");
	            System.out.print("Enter your choice: ");
	            
	            int choice = getIntInput();
	            
	            switch (choice) {
	                case 1:
	                    manageDoctors();
	                    break;
	                case 2:
	                    managePatients();
	                    break;
	                case 3:
	                    manageAppointments();
	                    break;
	                case 4:
	                    manageBilling();
	                    break;
	                case 5:
	                    logout = true;
	                    currentUser = null;
	                    System.out.println("Logged out successfully!");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	    
	    /**
	     * Manage doctors (Admin)
	     */
	    private static void manageDoctors() {
	        boolean back = false;
	        
	        while (!back) {
	            System.out.println("\n===== Manage Doctors =====");
	            System.out.println("1. Add Doctor");
	            System.out.println("2. View All Doctors");
	            System.out.println("3. Update Doctor");
	            System.out.println("4. Delete Doctor");
	            System.out.println("5. Back to Dashboard");
	            System.out.print("Enter your choice: ");
	            
	            int choice = getIntInput();
	            
	            switch (choice) {
	                case 1:
	                    addDoctor();
	                    break;
	                case 2:
	                    viewAllDoctors();
	                    break;
	                case 3:
	                    updateDoctor();
	                    break;
	                case 4:
	                    deleteDoctor();
	                    break;
	                case 5:
	                    back = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	    
	    /**
	     * Add a new doctor (Admin)
	     */
	    private static void addDoctor() {
	        System.out.println("\n===== Add Doctor =====");
	        
	        // Get user details
	        System.out.print("Enter name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter email: ");
	        String email = scanner.nextLine();
	        System.out.print("Enter password: ");
	        String password = scanner.nextLine();
	        
	        // Get doctor details
	        System.out.print("Enter specialization: ");
	        String specialization = scanner.nextLine();
	        System.out.print("Enter availability (e.g., Mon-Fri 9AM-5PM): ");
	        String availability = scanner.nextLine();
	        System.out.print("Enter contact number: ");
	        String contact = scanner.nextLine();
	        
	        try {
	            // Create user and doctor objects
	            User user = new User(name, email, password, "DOCTOR");
	            Doctor doctor = new Doctor(name, specialization, availability, contact, 0);
	            
	            // Register doctor
	            int doctorId = doctorService.registerDoctor(doctor, user);
	            System.out.println("Doctor added successfully with ID: " + doctorId);
	        } catch (Exception e) {
	            System.out.println("Failed to add doctor: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * View all doctors
	     */
	    private static void viewAllDoctors() {
	        System.out.println("\n===== All Doctors =====");
	        
	        try {
	            List<Doctor> doctors = doctorService.getAllDoctors();
	            
	            if (doctors.isEmpty()) {
	                System.out.println("No doctors found.");
	                return;
	            }
	            
	            System.out.println("ID\tName\tSpecialization\tAvailability\tContact");
	            System.out.println("----------------------------------------------------------");
	            
	            for (Doctor doctor : doctors) {
	                System.out.println(doctor.getId() + "\t" + doctor.getName() + "\t" + 
	                                  doctor.getSpecialization() + "\t" + doctor.getAvailability() + 
	                                  "\t" + doctor.getContact());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve doctors: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Update a doctor (Admin)
	     */
	    private static void updateDoctor() {
	        System.out.println("\n===== Update Doctor =====");
	        
	        System.out.print("Enter doctor ID to update: ");
	        int doctorId = getIntInput();
	        
	        try {
	            Doctor doctor = doctorService.getDoctorById(doctorId);
	            
	            System.out.println("Current details:");
	            System.out.println("Name: " + doctor.getName());
	            System.out.println("Specialization: " + doctor.getSpecialization());
	            System.out.println("Availability: " + doctor.getAvailability());
	            System.out.println("Contact: " + doctor.getContact());
	            
	            System.out.println("\nEnter new details (leave blank to keep current value):");
	            
	            System.out.print("Enter name: ");
	            String name = scanner.nextLine();
	            if (!name.isEmpty()) {
	                doctor.setName(name);
	            }
	            
	            System.out.print("Enter specialization: ");
	            String specialization = scanner.nextLine();
	            if (!specialization.isEmpty()) {
	                doctor.setSpecialization(specialization);
	            }
	            
	            System.out.print("Enter availability: ");
	            String availability = scanner.nextLine();
	            if (!availability.isEmpty()) {
	                doctor.setAvailability(availability);
	            }
	            
	            System.out.print("Enter contact: ");
	            String contact = scanner.nextLine();
	            if (!contact.isEmpty()) {
	                doctor.setContact(contact);
	            }
	            
	            boolean updated = doctorService.updateDoctor(doctor);
	            if (updated) {
	                System.out.println("Doctor updated successfully!");
	            } else {
	                System.out.println("Failed to update doctor.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Delete a doctor (Admin)
	     */
	    private static void deleteDoctor() {
	        System.out.println("\n===== Delete Doctor =====");
	        
	        System.out.print("Enter doctor ID to delete: ");
	        int doctorId = getIntInput();
	        
	        try {
	            boolean deleted = doctorService.deleteDoctor(doctorId);
	            if (deleted) {
	                System.out.println("Doctor deleted successfully!");
	            } else {
	                System.out.println("Failed to delete doctor.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Manage patients (Admin)
	     */
	    private static void managePatients() {
	        boolean back = false;
	        
	        while (!back) {
	            System.out.println("\n===== Manage Patients =====");
	            System.out.println("1. View All Patients");
	            System.out.println("2. View Patient Details");
	            System.out.println("3. Update Patient");
	            System.out.println("4. Delete Patient");
	            System.out.println("5. Back to Dashboard");
	            System.out.print("Enter your choice: ");
	            
	            int choice = getIntInput();
	            
	            switch (choice) {
	                case 1:
	                    viewAllPatients();
	                    break;
	                case 2:
	                    viewPatientDetails();
	                    break;
	                case 3:
	                    updatePatient();
	                    break;
	                case 4:
	                    deletePatient();
	                    break;
	                case 5:
	                    back = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	    
	    /**
	     * View all patients
	     */
	    private static void viewAllPatients() {
	        System.out.println("\n===== All Patients =====");
	        
	        try {
	            List<Patient> patients = patientService.getAllPatients();
	            
	            if (patients.isEmpty()) {
	                System.out.println("No patients found.");
	                return;
	            }
	            
	            System.out.println("ID\tName\tAge\tGender\tContact");
	            System.out.println("------------------------------------------");
	            
	            for (Patient patient : patients) {
	                System.out.println(patient.getId() + "\t" + patient.getName() + "\t" + 
	                                  patient.getAge() + "\t" + patient.getGender() + 
	                                  "\t" + patient.getContact());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve patients: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * View patient details
	     */
	    private static void viewPatientDetails() {
	        System.out.println("\n===== View Patient Details =====");
	        
	        System.out.print("Enter patient ID: ");
	        int patientId = getIntInput();
	        
	        try {
	            Patient patient = patientService.getPatientById(patientId);
	            
	            System.out.println("\nPatient Details:");
	            System.out.println("ID: " + patient.getId());
	            System.out.println("Name: " + patient.getName());
	            System.out.println("Age: " + patient.getAge());
	            System.out.println("Gender: " + patient.getGender());
	            System.out.println("Contact: " + patient.getContact());
	            System.out.println("Medical History: " + patient.getMedicalHistory());
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Update a patient
	     */
	    private static void updatePatient() {
	        System.out.println("\n===== Update Patient =====");
	        
	        System.out.print("Enter patient ID to update: ");
	        int patientId = getIntInput();
	        
	        try {
	            Patient patient = patientService.getPatientById(patientId);
	            
	            System.out.println("Current details:");
	            System.out.println("Name: " + patient.getName());
	            System.out.println("Age: " + patient.getAge());
	            System.out.println("Gender: " + patient.getGender());
	            System.out.println("Contact: " + patient.getContact());
	            System.out.println("Medical History: " + patient.getMedicalHistory());
	            
	            System.out.println("\nEnter new details (leave blank to keep current value):");
	            
	            System.out.print("Enter name: ");
	            String name = scanner.nextLine();
	            if (!name.isEmpty()) {
	                patient.setName(name);
	            }
	            
	            System.out.print("Enter age: ");
	            String ageStr = scanner.nextLine();
	            if (!ageStr.isEmpty()) {
	                patient.setAge(Integer.parseInt(ageStr));
	            }
	            
	            System.out.print("Enter gender: ");
	            String gender = scanner.nextLine();
	            if (!gender.isEmpty()) {
	                patient.setGender(gender);
	            }
	            
	            System.out.print("Enter contact: ");
	            String contact = scanner.nextLine();
	            if (!contact.isEmpty()) {
	                patient.setContact(contact);
	            }
	            
	            System.out.print("Enter medical history: ");
	            String medicalHistory = scanner.nextLine();
	            if (!medicalHistory.isEmpty()) {
	                patient.setMedicalHistory(medicalHistory);
	            }
	            
	            boolean updated = patientService.updatePatient(patient);
	            if (updated) {
	                System.out.println("Patient updated successfully!");
	            } else {
	                System.out.println("Failed to update patient.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Delete a patient
	     */
	    private static void deletePatient() {
	        System.out.println("\n===== Delete Patient =====");
	        
	        System.out.print("Enter patient ID to delete: ");
	        int patientId = getIntInput();
	        
	        try {
	            boolean deleted = patientService.deletePatient(patientId);
	            if (deleted) {
	                System.out.println("Patient deleted successfully!");
	            } else {
	                System.out.println("Failed to delete patient.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Manage appointments (Admin)
	     */
	    private static void manageAppointments() {
	        boolean back = false;
	        
	        while (!back) {
	            System.out.println("\n===== Manage Appointments =====");
	            System.out.println("1. View All Appointments");
	            System.out.println("2. Schedule Appointment");
	            System.out.println("3. Update Appointment");
	            System.out.println("4. Cancel Appointment");
	            System.out.println("5. Complete Appointment");
	            System.out.println("6. Back to Dashboard");
	            System.out.print("Enter your choice: ");
	            
	            int choice = getIntInput();
	            
	            switch (choice) {
	                case 1:
	                    viewAllAppointments();
	                    break;
	                case 2:
	                    scheduleAppointment();
	                    break;
	                case 3:
	                    updateAppointment();
	                    break;
	                case 4:
	                    cancelAppointment();
	                    break;
	                case 5:
	                    completeAppointment();
	                    break;
	                case 6:
	                    back = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	    
	    /**
	     * View all appointments
	     */
	    private static void viewAllAppointments() {
	        System.out.println("\n===== All Appointments =====");
	        
	        try {
	            List<Appointment> appointments = appointmentService.getAllAppointments();
	            
	            if (appointments.isEmpty()) {
	                System.out.println("No appointments found.");
	                return;
	            }
	            
	            System.out.println("ID\tPatient ID\tDoctor ID\tDate\tStatus");
	            System.out.println("----------------------------------------------------------");
	            
	            for (Appointment appointment : appointments) {
	                System.out.println(appointment.getId() + "\t" + appointment.getPatientId() + "\t" + 
	                                  appointment.getDoctorId() + "\t" + appointment.getAppointmentDate() + 
	                                  "\t" + appointment.getStatus());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve appointments: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Schedule an appointment
	     */
	    private static void scheduleAppointment() {
	        System.out.println("\n===== Schedule Appointment =====");
	        
	        System.out.print("Enter patient ID: ");
	        int patientId = getIntInput();
	        
	        System.out.print("Enter doctor ID: ");
	        int doctorId = getIntInput();
	        
	        System.out.println("Enter appointment date and time:");
	        System.out.print("Year (e.g., 2023): ");
	        int year = getIntInput();
	        System.out.print("Month (1-12): ");
	        int month = getIntInput();
	        System.out.print("Day (1-31): ");
	        int day = getIntInput();
	        System.out.print("Hour (0-23): ");
	        int hour = getIntInput();
	        System.out.print("Minute (0-59): ");
	        int minute = getIntInput();
	        
	        try {
	            LocalDateTime appointmentDate = LocalDateTime.of(year, month, day, hour, minute);
	            Appointment appointment = new Appointment(patientId, doctorId, appointmentDate, "SCHEDULED");
	            
	            int appointmentId = appointmentService.scheduleAppointment(appointment);
	            System.out.println("Appointment scheduled successfully with ID: " + appointmentId);
	        } catch (Exception e) {
	            System.out.println("Failed to schedule appointment: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Update an appointment
	     */
	    private static void updateAppointment() {
	        System.out.println("\n===== Update Appointment =====");
	        
	        System.out.print("Enter appointment ID to update: ");
	        int appointmentId = getIntInput();
	        
	        try {
	            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
	            
	            System.out.println("Current details:");
	            System.out.println("Patient ID: " + appointment.getPatientId());
	            System.out.println("Doctor ID: " + appointment.getDoctorId());
	            System.out.println("Date: " + appointment.getAppointmentDate());
	            System.out.println("Status: " + appointment.getStatus());
	            
	            System.out.println("\nEnter new details (leave blank to keep current value):");
	            
	            System.out.print("Enter patient ID: ");
	            String patientIdStr = scanner.nextLine();
	            if (!patientIdStr.isEmpty()) {
	                appointment.setPatientId(Integer.parseInt(patientIdStr));
	            }
	            
	            System.out.print("Enter doctor ID: ");
	            String doctorIdStr = scanner.nextLine();
	            if (!doctorIdStr.isEmpty()) {
	                appointment.setDoctorId(Integer.parseInt(doctorIdStr));
	            }
	            
	            System.out.println("Update appointment date and time? (Y/N): ");
	            String updateDate = scanner.nextLine();
	            if (updateDate.equalsIgnoreCase("Y")) {
	                System.out.print("Year (e.g., 2023): ");
	                int year = getIntInput();
	                System.out.print("Month (1-12): ");
	                int month = getIntInput();
	                System.out.print("Day (1-31): ");
	                int day = getIntInput();
	                System.out.print("Hour (0-23): ");
	                int hour = getIntInput();
	                System.out.print("Minute (0-59): ");
	                int minute = getIntInput();
	                
	                LocalDateTime appointmentDate = LocalDateTime.of(year, month, day, hour, minute);
	                appointment.setAppointmentDate(appointmentDate);
	            }
	            
	            System.out.print("Enter status (SCHEDULED/COMPLETED/CANCELLED): ");
	            String status = scanner.nextLine();
	            if (!status.isEmpty()) {
	                appointment.setStatus(status);
	            }
	            
	            boolean updated = appointmentService.updateAppointment(appointment);
	            if (updated) {
	                System.out.println("Appointment updated successfully!");
	            } else {
	                System.out.println("Failed to update appointment.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Cancel an appointment
	     */
	    private static void cancelAppointment() {
	        System.out.println("\n===== Cancel Appointment =====");
	        
	        System.out.print("Enter appointment ID to cancel: ");
	        int appointmentId = getIntInput();
	        
	        try {
	            boolean cancelled = appointmentService.cancelAppointment(appointmentId);
	            if (cancelled) {
	                System.out.println("Appointment cancelled successfully!");
	            } else {
	                System.out.println("Failed to cancel appointment.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Complete an appointment
	     */
	    private static void completeAppointment() {
	        System.out.println("\n===== Complete Appointment =====");
	        
	        System.out.print("Enter appointment ID to mark as completed: ");
	        int appointmentId = getIntInput();
	        
	        try {
	            boolean completed = appointmentService.completeAppointment(appointmentId);
	            if (completed) {
	                System.out.println("Appointment marked as completed successfully!");
	            } else {
	                System.out.println("Failed to complete appointment.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Manage billing (Admin)
	     */
	    private static void manageBilling() {
	        boolean back = false;
	        
	        while (!back) {
	            System.out.println("\n===== Manage Billing =====");
	            System.out.println("1. View All Billing Records");
	            System.out.println("2. Generate Bill");
	            System.out.println("3. Update Bill");
	            System.out.println("4. Mark Bill as Paid");
	            System.out.println("5. Cancel Bill");
	            System.out.println("6. Back to Dashboard");
	            System.out.print("Enter your choice: ");
	            
	            int choice = getIntInput();
	            
	            switch (choice) {
	                case 1:
	                    viewAllBillings();
	                    break;
	                case 2:
	                    generateBill();
	                    break;
	                case 3:
	                    updateBill();
	                    break;
	                case 4:
	                    markBillAsPaid();
	                    break;
	                case 5:
	                    cancelBill();
	                    break;
	                case 6:
	                    back = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	    }
	    
	    /**
	     * View all billing records
	     */
	    private static void viewAllBillings() {
	        System.out.println("\n===== All Billing Records =====");
	        
	        try {
	            List<Billing> billings = billingService.getAllBillings();
	            
	            if (billings.isEmpty()) {
	                System.out.println("No billing records found.");
	                return;
	            }
	            
	            System.out.println("ID\tPatient ID\tAmount\tStatus\tDate");
	            System.out.println("----------------------------------------------------------");
	            
	            for (Billing billing : billings) {
	                System.out.println(billing.getId() + "\t" + billing.getPatientId() + "\t" + 
	                                  billing.getAmount() + "\t" + billing.getPaymentStatus() + 
	                                  "\t" + billing.getBillingDate());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve billing records: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Generate a bill
	     */
	    private static void generateBill() {
	        System.out.println("\n===== Generate Bill =====");
	        
	        System.out.print("Enter patient ID: ");
	        int patientId = getIntInput();
	        
	        System.out.print("Enter amount: ");
	        double amount = getDoubleInput();
	        
	        try {
	            Billing billing = new Billing(patientId, amount, "PENDING", LocalDateTime.now());
	            
	            int billingId = billingService.generateBill(billing);
	            System.out.println("Bill generated successfully with ID: " + billingId);
	        } catch (Exception e) {
	            System.out.println("Failed to generate bill: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Update a bill
	     */
	    private static void updateBill() {
	        System.out.println("\n===== Update Bill =====");
	        
	        System.out.print("Enter billing ID to update: ");
	        int billingId = getIntInput();
	        
	        try {
	            Billing billing = billingService.getBillingById(billingId);
	            
	            System.out.println("Current details:");
	            System.out.println("Patient ID: " + billing.getPatientId());
	            System.out.println("Amount: " + billing.getAmount());
	            System.out.println("Status: " + billing.getPaymentStatus());
	            System.out.println("Date: " + billing.getBillingDate());
	            
	            System.out.println("\nEnter new details (leave blank to keep current value):");
	            
	            System.out.print("Enter patient ID: ");
	            String patientIdStr = scanner.nextLine();
	            if (!patientIdStr.isEmpty()) {
	                billing.setPatientId(Integer.parseInt(patientIdStr));
	            }
	            
	            System.out.print("Enter amount: ");
	            String amountStr = scanner.nextLine();
	            if (!amountStr.isEmpty()) {
	                billing.setAmount(Double.parseDouble(amountStr));
	            }
	            
	            System.out.print("Enter status (PENDING/PAID/CANCELLED): ");
	            String status = scanner.nextLine();
	            if (!status.isEmpty()) {
	                billing.setPaymentStatus(status);
	            }
	            
	            boolean updated = billingService.updateBilling(billing);
	            if (updated) {
	                System.out.println("Bill updated successfully!");
	            } else {
	                System.out.println("Failed to update bill.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Mark a bill as paid
	     */
	    private static void markBillAsPaid() {
	        System.out.println("\n===== Mark Bill as Paid =====");
	        
	        System.out.print("Enter billing ID to mark as paid: ");
	        int billingId = getIntInput();
	        
	        try {
	            boolean marked = billingService.markAsPaid(billingId);
	            if (marked) {
	                System.out.println("Bill marked as paid successfully!");
	            } else {
	                System.out.println("Failed to mark bill as paid.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Cancel a bill
	     */
	    private static void cancelBill() {
	        System.out.println("\n===== Cancel Bill =====");
	        
	        System.out.print("Enter billing ID to cancel: ");
	        int billingId = getIntInput();
	        
	        try {
	            boolean cancelled = billingService.cancelBilling(billingId);
	            if (cancelled) {
	                System.out.println("Bill cancelled successfully!");
	            } else {
	                System.out.println("Failed to cancel bill.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Show doctor dashboard
	     */
	    private static void showDoctorDashboard() {
	        boolean logout = false;
	        
	        try {
	            // Get doctor details
	            Doctor doctor = doctorService.getDoctorByUserId(currentUser.getId());
	            
	            while (!logout) {
	                System.out.println("\n===== Doctor Dashboard =====");
	                System.out.println("Welcome, Dr. " + doctor.getName() + "!");
	                System.out.println("1. View My Appointments");
	                System.out.println("2. Complete Appointment");
	                System.out.println("3. View Patient Details");
	                System.out.println("4. Update My Profile");
	                System.out.println("5. Logout");
	                System.out.print("Enter your choice: ");
	                
	                int choice = getIntInput();
	                
	                switch (choice) {
	                    case 1:
	                        viewDoctorAppointments(doctor.getId());
	                        break;
	                    case 2:
	                        completeAppointment();
	                        break;
	                    case 3:
	                        viewPatientDetails();
	                        break;
	                    case 4:
	                        updateDoctorProfile(doctor);
	                        break;
	                    case 5:
	                        logout = true;
	                        currentUser = null;
	                        System.out.println("Logged out successfully!");
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * View doctor's appointments
	     */
	    private static void viewDoctorAppointments(int doctorId) {
	        System.out.println("\n===== My Appointments =====");
	        
	        try {
	            List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
	            
	            if (appointments.isEmpty()) {
	                System.out.println("No appointments found.");
	                return;
	            }
	            
	            System.out.println("ID\tPatient ID\tDate\tStatus");
	            System.out.println("------------------------------------------");
	            
	            for (Appointment appointment : appointments) {
	                System.out.println(appointment.getId() + "\t" + appointment.getPatientId() + "\t" + 
	                                  appointment.getAppointmentDate() + "\t" + appointment.getStatus());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve appointments: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Update doctor profile
	     */
	    private static void updateDoctorProfile(Doctor doctor) {
	        System.out.println("\n===== Update My Profile =====");
	        
	        try {
	            System.out.println("Current details:");
	            System.out.println("Name: " + doctor.getName());
	            System.out.println("Specialization: " + doctor.getSpecialization());
	            System.out.println("Availability: " + doctor.getAvailability());
	            System.out.println("Contact: " + doctor.getContact());
	            
	            System.out.println("\nEnter new details (leave blank to keep current value):");
	            
	            System.out.print("Enter name: ");
	            String name = scanner.nextLine();
	            if (!name.isEmpty()) {
	                doctor.setName(name);
	            }
	            
	            System.out.print("Enter specialization: ");
	            String specialization = scanner.nextLine();
	            if (!specialization.isEmpty()) {
	                doctor.setSpecialization(specialization);
	            }
	            
	            System.out.print("Enter availability: ");
	            String availability = scanner.nextLine();
	            if (!availability.isEmpty()) {
	                doctor.setAvailability(availability);
	            }
	            
	            System.out.print("Enter contact: ");
	            String contact = scanner.nextLine();
	            if (!contact.isEmpty()) {
	                doctor.setContact(contact);
	            }
	            
	            boolean updated = doctorService.updateDoctor(doctor);
	            if (updated) {
	                System.out.println("Profile updated successfully!");
	            } else {
	                System.out.println("Failed to update profile.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Show patient dashboard
	     */
	    private static void showPatientDashboard() {
	        boolean logout = false;
	        
	        try {
	            // Get patient details
	            Patient patient = patientService.getPatientByUserId(currentUser.getId());
	            
	            while (!logout) {
	                System.out.println("\n===== Patient Dashboard =====");
	                System.out.println("Welcome, " + patient.getName() + "!");
	                System.out.println("1. View My Appointments");
	                System.out.println("2. Schedule Appointment");
	                System.out.println("3. Cancel Appointment");
	                System.out.println("4. View My Bills");
	                System.out.println("5. Update My Profile");
	                System.out.println("6. Logout");
	                System.out.print("Enter your choice: ");
	                
	                int choice = getIntInput();
	                
	                switch (choice) {
	                    case 1:
	                        viewPatientAppointments(patient.getId());
	                        break;
	                    case 2:
	                        schedulePatientAppointment(patient.getId());
	                        break;
	                    case 3:
	                        cancelPatientAppointment(patient.getId());
	                        break;
	                    case 4:
	                        viewPatientBills(patient.getId());
	                        break;
	                    case 5:
	                        updatePatientProfile(patient);
	                        break;
	                    case 6:
	                        logout = true;
	                        currentUser = null;
	                        System.out.println("Logged out successfully!");
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * View patient's appointments
	     */
	    private static void viewPatientAppointments(int patientId) {
	        System.out.println("\n===== My Appointments =====");
	        
	        try {
	            List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
	            
	            if (appointments.isEmpty()) {
	                System.out.println("No appointments found.");
	                return;
	            }
	            
	            System.out.println("ID\tDoctor ID\tDate\tStatus");
	            System.out.println("------------------------------------------");
	            
	            for (Appointment appointment : appointments) {
	                System.out.println(appointment.getId() + "\t" + appointment.getDoctorId() + "\t" + 
	                                  appointment.getAppointmentDate() + "\t" + appointment.getStatus());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve appointments: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Schedule appointment for patient
	     */
	    private static void schedulePatientAppointment(int patientId) {
	        System.out.println("\n===== Schedule Appointment =====");
	        
	        // Show available doctors
	        try {
	            List<Doctor> doctors = doctorService.getAllDoctors();
	            
	            if (doctors.isEmpty()) {
	                System.out.println("No doctors available.");
	                return;
	            }
	            
	            System.out.println("Available Doctors:");
	            System.out.println("ID\tName\tSpecialization\tAvailability");
	            System.out.println("------------------------------------------");
	            
	            for (Doctor doctor : doctors) {
	                System.out.println(doctor.getId() + "\t" + doctor.getName() + "\t" + 
	                                  doctor.getSpecialization() + "\t" + doctor.getAvailability());
	            }
	            
	            System.out.print("\nEnter doctor ID: ");
	            int doctorId = getIntInput();
	            
	            System.out.println("Enter appointment date and time:");
	            System.out.print("Year (e.g., 2023): ");
	            int year = getIntInput();
	            System.out.print("Month (1-12): ");
	            int month = getIntInput();
	            System.out.print("Day (1-31): ");
	            int day = getIntInput();
	            System.out.print("Hour (0-23): ");
	            int hour = getIntInput();
	            System.out.print("Minute (0-59): ");
	            int minute = getIntInput();
	            
	            LocalDateTime appointmentDate = LocalDateTime.of(year, month, day, hour, minute);
	            Appointment appointment = new Appointment(patientId, doctorId, appointmentDate, "SCHEDULED");
	            
	            int appointmentId = appointmentService.scheduleAppointment(appointment);
	            System.out.println("Appointment scheduled successfully with ID: " + appointmentId);
	        } catch (Exception e) {
	            System.out.println("Failed to schedule appointment: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Cancel patient's appointment
	     */
	    private static void cancelPatientAppointment(int patientId) {
	        System.out.println("\n===== Cancel Appointment =====");
	        
	        try {
	            // Show patient's appointments
	            List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
	            
	            if (appointments.isEmpty()) {
	                System.out.println("No appointments found.");
	                return;
	            }
	            
	            System.out.println("Your Appointments:");
	            System.out.println("ID\tDoctor ID\tDate\tStatus");
	            System.out.println("------------------------------------------");
	            
	            for (Appointment appointment : appointments) {
	                System.out.println(appointment.getId() + "\t" + appointment.getDoctorId() + "\t" + 
	                                  appointment.getAppointmentDate() + "\t" + appointment.getStatus());
	            }
	            
	            System.out.print("\nEnter appointment ID to cancel: ");
	            int appointmentId = getIntInput();
	            
	            boolean cancelled = appointmentService.cancelAppointment(appointmentId);
	            if (cancelled) {
	                System.out.println("Appointment cancelled successfully!");
	            } else {
	                System.out.println("Failed to cancel appointment.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * View patient's bills
	     */
	    private static void viewPatientBills(int patientId) {
	        System.out.println("\n===== My Bills =====");
	        
	        try {
	            List<Billing> billings = billingService.getBillingsByPatientId(patientId);
	            
	            if (billings.isEmpty()) {
	                System.out.println("No billing records found.");
	                return;
	            }
	            
	            System.out.println("ID\tAmount\tStatus\tDate");
	            System.out.println("------------------------------------------");
	            
	            for (Billing billing : billings) {
	                System.out.println(billing.getId() + "\t" + billing.getAmount() + "\t" + 
	                                  billing.getPaymentStatus() + "\t" + billing.getBillingDate());
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to retrieve billing records: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Update patient profile
	     */
	    private static void updatePatientProfile(Patient patient) {
	        System.out.println("\n===== Update My Profile =====");
	        
	        try {
	            System.out.println("Current details:");
	            System.out.println("Name: " + patient.getName());
	            System.out.println("Age: " + patient.getAge());
	            System.out.println("Gender: " + patient.getGender());
	            System.out.println("Contact: " + patient.getContact());
	            
	            System.out.println("\nEnter new details (leave blank to keep current value):");
	            
	            System.out.print("Enter name: ");
	            String name = scanner.nextLine();
	            if (!name.isEmpty()) {
	                patient.setName(name);
	            }
	            
	            System.out.print("Enter age: ");
	            String ageStr = scanner.nextLine();
	            if (!ageStr.isEmpty()) {
	                patient.setAge(Integer.parseInt(ageStr));
	            }
	            
	            System.out.print("Enter gender: ");
	            String gender = scanner.nextLine();
	            if (!gender.isEmpty()) {
	                patient.setGender(gender);
	            }
	            
	            System.out.print("Enter contact: ");
	            String contact = scanner.nextLine();
	            if (!contact.isEmpty()) {
	                patient.setContact(contact);
	            }
	            
	            boolean updated = patientService.updatePatient(patient);
	            if (updated) {
	                System.out.println("Profile updated successfully!");
	            } else {
	                System.out.println("Failed to update profile.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Helper method to get integer input
	     */
	    private static int getIntInput() {
	        while (true) {
	            try {
	                String input = scanner.nextLine();
	                return Integer.parseInt(input);
	            } catch (NumberFormatException e) {
	                System.out.print("Invalid input. Please enter a number: ");
	            }
	        }
	    }
	    
	    /**
	     * Helper method to get double input
	     */
	    private static double getDoubleInput() {
	        while (true) {
	            try {
	                String input = scanner.nextLine();
	                return Double.parseDouble(input);
	            } catch (NumberFormatException e) {
	                System.out.print("Invalid input. Please enter a number: ");
	            }
	        }
	    }
	

}
