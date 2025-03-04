-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS users;

-- Use the database
USE users;

-- Drop the users table if it exists
DROP TABLE IF EXISTS users;

-- Create the users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID
    name VARCHAR(100) NOT NULL,             -- Username
    password VARCHAR(255) NOT NULL,         -- Password
    role ENUM('admin', 'client') NOT NULL   -- Role (admin/client)
);

-- Insert data into users table
INSERT INTO users (name, password, role) VALUES
('admin1', '123', 'admin'),
('client1', '123', 'client'),
('client2', '123', 'client'),
('admin2', '123', 'admin'),
('client3', '123', 'client');
