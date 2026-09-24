# 💊 Pharmacy Management System

A desktop application designed to manage pharmacy inventory, record sales, and process transactions efficiently. Built with Java, JavaFX, and MySQL using JDBC for direct database interaction.

---

## 📌 Features

* **Inventory Management:** Add, update, view, and manage pharmaceutical products and stock levels.
* **Sales & Billing:** Process sales transactions with real-time stock updates.
* **Database Integration:** Persistent storage using MySQL with clean DAO (Data Access Object) architecture.
* **User Interface:** Interactive Desktop GUI built using JavaFX and Scene Builder.

---

## 🛠️ Tech Stack & Architecture

* **Language:** Java (JDK 17+)
* **GUI Framework:** JavaFX (FXML + CSS)
* **Database:** MySQL
* **Database Connectivity:** JDBC (Java Database Connectivity)
* **Build & Dependency Management:** Apache Maven
* **Architecture Pattern:** MVC (Model-View-Controller) / DAO Pattern

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your machine:
* **Java Development Kit (JDK):** Version 17 or higher
* **MySQL Server:** Version 8.0 or higher
* **IDE:** IntelliJ IDEA (recommended) or Eclipse

### Database Setup

1. Open your MySQL client (e.g., MySQL Workbench or Command Line).

## 📂 Project Structure

```text
Pharmacy_System/
├── .mvn/
│   └── wrapper/             # Maven Wrapper configuration
├── src/
│   └── main/
│       ├── java/
│       │   └── io/github/kurodarck/pharmacysystem/
│       │       ├── config/         # Database connection settings & utilities
│       │       ├── controller/     # JavaFX Controller classes (UI logic)
│       │       ├── dao/            # Data Access Object pattern (JDBC queries)
│       │       ├── model/          # Entity classes (Product, Sale, User, etc.)
│       │       ├── DatabaseTest.java
│       │       ├── Launcher.java   # Application entry point launcher
│       │       └── PharmacyApplication.java
│       │   └── module-info.java   # JavaFX Module Configuration
│       └── resources/
│           ├── Image/              # Application UI icons and images
│           ├── io/github/kurodarck/pharmacysystem/  # FXML view layouts & CSS
│           └── config.properties   # App configuration properties
├── Pharmacy_system_DataBase.sql   # Database initialization script
├── pom.xml                         # Maven dependencies & build settings
└── README.md
3. Execute the provided SQL script located in the project root:
   ```sql
   -- Run Pharmacy_system_DataBase script to create tables and seed initial data
