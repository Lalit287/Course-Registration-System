-- 1. Creating database
CREATE DATABASE course_db;

-- 2. Using the database
USE course_db;

-- 3. Create table
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(100) NOT NULL,
    duration VARCHAR(50),
    fee DECIMAL(10,2)
);
