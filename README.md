# 🏥 Sunrise Dental Clinic Management System

![Java](https://img.shields.io/badge/Java-SE_17-blue.svg)
![MySQL](https://img.shields.io/badge/MySQL-MariaDB-orange.svg)
![Apache Ant](https://img.shields.io/badge/Build-Apache_Ant-red.svg)
![JUnit](https://img.shields.io/badge/Testing-JUnit_4-green.svg)

## 📌 Project Overview
The Sunrise Dental Clinic Management System is a robust, highly secure, enterprise-level desktop application developed to transition traditional paper-based clinic workflows into an automated digital infrastructure. 

This project was developed as the final assessment for the **CIS 6003 Advanced Programming** module at **ICBT Campus / Cardiff Metropolitan University**.

## 🏗️ Architecture & Design Patterns
The application is strictly engineered using a **3-Tier Architecture**, completely decoupling the Presentation, Business Logic, and Data Access layers[cite: 4].
* **Presentation Tier:** Java Swing (UI Forms)[cite: 4]
* **Business Logic Tier:** Java Controllers[cite: 4]
* **Data Access Tier:** MySQL / DAOs[cite: 4]

**Implemented GoF Design Patterns:**
* **Singleton:** To manage a single, thread-safe MySQL connection pool and prevent memory leaks[cite: 4].
* **Data Access Object (DAO):** To abstract raw SQL queries away from the Java business logic[cite: 4].
* **Facade:** To wrap complex subsystems into unified interfaces for the frontend[cite: 4].

## ✨ Advanced Features
* **🔐 Cryptographic Security (RBAC):** Strict Role-Based Access Control (Admin, Doctor, Receptionist) utilizing `MessageDigest` SHA-256 password hashing[cite: 4].
* **📧 Asynchronous SMTP Emails:** Background-threaded email dispatch to send appointment confirmations without freezing the Java Swing UI[cite: 4].
* **🖨️ Web/PDF Integration:** Generates dynamic HTML receipts and utilizes OS-level integration (`java.awt.Desktop`) to launch Google Chrome for seamless PDF printing[cite: 4].
* **📊 Database Optimization:** Utilizes MySQL **Stored Procedures** (`sp_GetDailyReport`) to offload heavy financial data aggregation to the database engine[cite: 4].

## 🧪 Testing & Quality Assurance
Developed using **Test-Driven Development (TDD)**. The system includes a comprehensive suite of **130 Test Cases**[cite: 4]:
* **25 Automated JUnit Tests:** Validating regex, SHA-256 hashing, financial calculations, and POJO instantiation[cite: 4].
* **105 Manual/UI Tests:** Covering Equivalence Partitioning, Boundary Value Analysis, and End-to-End System workflows[cite: 4].

## ⚙️ Setup & Installation
1. **Prerequisites:** Java JDK 17+, Apache Ant, and XAMPP (for MySQL/MariaDB)[cite: 4].
2. **Database Setup:** 
   * Start Apache and MySQL in XAMPP.
   * Open phpMyAdmin and create a database named `sunrisedental`.
   * Import your SQL schema to generate the `users`, `patients`, `treatments`, and `appointments` tables.
3. **Dependencies:** Ensure `mysql-connector-java-5.1.49.jar` and `javax.mail.jar` are added to your project libraries[cite: 4].
4. **Build & Run:** 
   * Open the project in Apache NetBeans.
   * Run `Clean and Build` to execute the Ant `build.xml`.
   * Run `LoginForm.java` to launch the application.

## 🎓 Academic Information
* **Author:** Raveen Sandinu Abeydeera
* **Student ID:** 20315281
* **Institution:** ICBT Campus / Cardiff Metropolitan University
* **Module:** Advanced Programming (CIS6003)
