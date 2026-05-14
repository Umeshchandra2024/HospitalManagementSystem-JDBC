-- Hospital Management System — MySQL schema
-- Run: mysql -u root -p < database/schema.sql

CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    CONSTRAINT fk_appt_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_appt_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    UNIQUE KEY uq_doctor_date (doctor_id, appointment_date)
);

-- Sample doctors (run once on a new database; skip if you already have data)
INSERT INTO doctors (name, specialization) VALUES
    ('Dr. Sarah Chen', 'Cardiology'),
    ('Dr. James Okonkwo', 'General Medicine'),
    ('Dr. Priya Sharma', 'Pediatrics');
