# Hospital Management System

![Hospital Management System](https://img.shields.io/badge/Project-Hospital%20Management%20System-blue)
![Java](https://img.shields.io/badge/Language-Java-orange)
![MySQL](https://img.shields.io/badge/Database-MySQL-green)

A comprehensive Java-based Hospital Management System with MySQL database integration for efficient management of hospital operations, patient records, appointments, and billing.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Database Design](#database-design)
- [Installation](#installation)
- [Usage](#usage)
- [Default Credentials](#default-credentials)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)

## 📝 Overview

This Hospital Management System is designed to streamline hospital operations with role-based access control:

- **Admin**: Full system control for managing hospital operations, staff, and resources
- **Doctor**: Access to patient records, appointment management, and treatment updates
- **Patient**: Self-service portal for appointment booking and medical history access

The system implements Object-Oriented Programming principles, exception handling, and utilizes the Java Collection Framework for efficient data management.

## ✨ Features

### 1. Patient Management
- Add, update, and delete patient records
- Store comprehensive patient information (personal details, medical history)
- Track patient visits and treatment plans

### 2. Doctor & Staff Management
- Maintain doctor profiles with specialization and availability
- Assign doctors to patients based on specialization and availability
- Manage staff scheduling and shift assignments

### 3. Appointment Management
- Schedule, reschedule, and cancel appointments
- View appointment history by patient or doctor
- Automated status tracking (scheduled, completed, cancelled)

### 4. Role-Based Access Control (RBAC)
- **Admin Dashboard**: Complete system management capabilities
- **Doctor Dashboard**: Patient and appointment management tools
- **Patient Dashboard**: Self-service appointment booking and history viewing

### 5. Billing & Payment Management
- Generate bills for consultations and treatments
- Track payment status (pending, paid, cancelled)
- Maintain billing history for accounting purposes

## 🏗️ System Architecture

The system follows a layered architecture pattern:

1. **Presentation Layer**: Console-based user interface
2. **Service Layer**: Business logic implementation
3. **Data Access Layer**: Database operations using DAO pattern
4. **Model Layer**: Entity classes representing domain objects
5. **Utility Layer**: Helper classes and database connection management

## 🗄️ Database Design

The system uses a relational database with the following tables:

1. **users**
   - `id` (PK), `name`, `email`, `password`, `role`, `created_at`

2. **patients**
   - `id` (PK), `name`, `age`, `gender`, `contact`, `medical_history`, `user_id` (FK), `created_at`

3. **doctors**
   - `id` (PK), `name`, `specialization`, `availability`, `contact`, `user_id` (FK), `created_at`

4. **appointments**
   - `id` (PK), `patient_id` (FK), `doctor_id` (FK), `appointment_date`, `status`, `created_at`

5. **billing**
   - `id` (PK), `patient_id` (FK), `amount`, `payment_status`, `billing_date`, `created_at`

## 🚀 Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC driver)

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/hospital-management-system.git
cd hospital-management-system
