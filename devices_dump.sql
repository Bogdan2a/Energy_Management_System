-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS devices;

-- Use the database
USE devices;

-- Drop the devices table if it exists
DROP TABLE IF EXISTS devices;

-- Create the devices table
CREATE TABLE devices (
    id INT AUTO_INCREMENT PRIMARY KEY,                  -- Device ID
    description VARCHAR(255) NOT NULL,                 -- Device description
    address VARCHAR(255) NOT NULL,                      -- Device address
    max_hourly_consumption DECIMAL(10, 2) NOT NULL,    -- Maximum hourly energy consumption
    UNIQUE(description, address)                        -- Ensure unique device address and description
);

DROP TABLE IF EXISTS user_devices;

CREATE TABLE user_devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,              -- New auto-incremented primary key
    user_id INT NOT NULL,                              -- ID of the user
    device_id INT NOT NULL,                            -- ID of the device
    UNIQUE (user_id, device_id)                        -- Ensure uniqueness for user-device pair
);

-- Populate the devices table with some sample data
INSERT INTO devices (description, address, max_hourly_consumption) VALUES
('Smart Meter', '123 Main St', 5.00),
('Solar Panel', '456 Elm St', 10.00),
('Smart Thermostat', '789 Oak St', 3.50),
('Electric Vehicle Charger', '101 Pine St', 15.00),
('Energy Storage System', '202 Maple St', 7.50);

-- Populate the user_devices table with some sample data
INSERT INTO user_devices (user_id, device_id) VALUES
(2, 1),
(3, 2);
