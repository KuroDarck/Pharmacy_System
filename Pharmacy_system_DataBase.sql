DROP DATABASE IF EXISTS Pharmacy_System;
CREATE DATABASE IF NOT EXISTS Pharmacy_System;
USE Pharmacy_System;
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    created DATETIME,
    updated DATETIME
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(255),
    unit_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    product_quantity INT,
    created DATETIME,
    updated DATETIME,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) UNIQUE NOT NULL,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(255),
    telephone VARCHAR(20),
    address VARCHAR(60),
    email VARCHAR(255),
    city VARCHAR(60),
    created DATETIME,
    updated DATETIME
);

CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) NOT NULL,
    full_name VARCHAR(60) NOT NULL,
    username VARCHAR(60) NOT NULL,
    address VARCHAR(60),
    telephone VARCHAR(20),
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20),
    created DATETIME,
    updated DATETIME
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(15) UNIQUE NOT NULL,
    full_name VARCHAR(60) NOT NULL,
    address VARCHAR(60),
    telephone VARCHAR(20),
    email VARCHAR(255),
    created DATETIME,
    updated DATETIME
);

CREATE TABLE purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT,
    employee_id INT,
    invoice_number VARCHAR(50),
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    created DATETIME,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_number VARCHAR(50),
    customer_id INT,
    employee_id INT,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00, 
    created DATETIME, 
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE purchase_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    purchase_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    purchase_amount INT,
    purchase_subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    purchase_id INT,
    product_id INT,
    FOREIGN KEY (purchase_id) REFERENCES purchases(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

