-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS monitoring;

-- Use the database
USE monitoring;

DROP TABLE IF EXISTS energy_measurements;
CREATE TABLE energy_measurements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp BIGINT NOT NULL,
    device_id VARCHAR(255) NOT NULL,
    measurement_value FLOAT NOT NULL
);
