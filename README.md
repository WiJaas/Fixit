This repository contains the complete source code for a JavaFX-based Incident Management System, meticulously designed to manage and resolve incidents within a software services company. The system employs the Model-View-Controller (MVC) architectural pattern, the Data Access Object (DAO) pattern for database interactions, and robust role-based access control. It offers distinct interfaces and functionalities tailored for Admins, Employees, and Technicians.

## Table of Contents

1.  [Overview](#overview)
2.  [Key Features](#key-features)
3.  [Technologies Used](#technologies-used)
    *   [Core Technologies](#core-technologies)
    *   [Libraries and Dependencies](#libraries-and-dependencies)
4.  [Architecture and Design](#architecture-and-design)
    *   [Model-View-Controller (MVC)](#model-view-controller-mvc)
    *   [Data Access Object (DAO) Pattern](#data-access-object-dao-pattern)
    *   [Role-Based Access Control (RBAC)](#role-based-access-control-rbac)
5.  [Project Structure](#project-structure)
    *   [Directory Structure](#directory-structure)
    *   [Package Descriptions](#package-descriptions)
6.  [User Roles and Functionality](#user-roles-and-functionality)
    *   [Administrator (Admin)](#administrator-admin)
    *   [Employee](#employee)
    *   [Technician](#technician)
7.  [Database Schema](#database-schema)
    *  [SQL Scripts Location](#sql-scripts-location)
    *   [Table Definitions](#table-definitions)
8.  [Getting Started](#getting-started)
    *   [Prerequisites](#prerequisites)
    *   [Installation](#installation)
    *  [Database Configuration](#database-configuration)
    *   [Running the Application](#running-the-application)
9.  [Detailed Usage Guide](#detailed-usage-guide)
    *   [Login Process](#login-process)
    *   [Admin Interface Usage](#admin-interface-usage)
    *   [Employee Interface Usage](#employee-interface-usage)
    *   [Technician Interface Usage](#technician-interface-usage)

## Overview

The Incident Management System (IMS) aims to provide a user-friendly and efficient platform for logging, managing, and resolving incidents within a software services company. It streamlines workflows, enhances communication, and ensures accountability in incident handling. This system is built to be scalable and maintainable, providing a robust solution for incident management needs.

## Key Features

*   **Incident Management:** Robust system for logging, tracking, and updating incidents.
*   **User Management:** Functionality for creating, editing, and managing user accounts.
*   **Role-Based Access Control:** Ensures different access levels based on user roles.
*   **Database Persistence:** Data is persistently stored in a database.
*   **Reporting and Analytics:** Tools for exporting data for analysis and reporting.
*   **Real-time Updates:** Status updates and notifications within the application (if implemented).
*   **User-Friendly Interface:** Designed for ease of use and intuitive navigation.
*  **Feedback Mechanism:** Allows users to provide feedback on resolved incidents.
*   **Incident Assignment:** Ability for technicians to assign incidents to themselves for resolution.

## Technologies Used

### Core Technologies

*   **JavaFX:** Used for the development of the Graphical User Interface (GUI).
*   **Maven:** For project management, build automation, and dependency handling.
*   **JDBC:** Used for database connectivity and interaction.
*   **MySQL**: Relational database used for persistent storage.


## Architecture and Design

### Model-View-Controller (MVC)

The system is structured following the MVC pattern:

*   **Model:** Represents the data and business logic of the application, including classes like `Incident.java` and `User.java`.
*   **View:** The user interface components built with JavaFX, typically described using FXML files.
*   **Controller:** Manages interactions between the Model and View, responding to user inputs and updating the UI accordingly.

### Data Access Object (DAO) Pattern

The DAO pattern decouples data access logic from the application logic:

*   **DAO Interfaces:** Define methods for database interactions (e.g., `IncidentDAO.java` interface)
*   **DAO Implementations:** Concrete classes implement these interfaces (e.g., `IncidentDAOImpl.java`), encapsulating database operations.

### Role-Based Access Control (RBAC)

RBAC manages user access and authorization:

*   **User Roles:** Defined as Admin, Employee, and Technician.
*   **Access Permissions:** Each role has specific access permissions to various features.
*   **Authentication and Authorization:** Processes ensure that users access the system based on their assigned roles.

## Project Structure

### Directory Structure

<img width="242" alt="1" src="https://github.com/user-attachments/assets/1546cd1d-8ef8-4cb0-a355-054f14c1825d" />
<img width="239" alt="2" src="https://github.com/user-attachments/assets/f9ee5f00-1850-4ccf-a2ce-899dfe26a3e9" />



## User Roles and Functionality

### Administrator (Admin)

*   **User Management:** Can create, edit, and delete user accounts.
*  **Incident Overview**: View all incidents in the system.
*   **Reporting:** Generate reports and export incident and user data.
*   **Navigation**: Access all functionalities through different navigation options.

### Employee

*   **Access:** Limited access to incident creation and tracking.
*   **Incident Logging:** Can log new incidents, providing detailed descriptions, type, and priority.
*   **Incident Tracking:** Can view the status of their reported incidents and provide feedback on them.

### Technician

*   **Access:** Access to manage and resolve open incidents.
*   **Incident Assignment:** Can assign open incidents to themselves for resolution.
*   **Incident Resolution:** Can update the status of assigned incidents and mark them as resolved.
*   **Incident Tracking**: Can view assigned and resolved incidents

## Database Schema

### SQL Scripts Location
SQL scripts for creating the database schema are located in the `sql` directory at the root level of the repository.

### Table Definitions

*   **`user` Table:** Stores user information (user ID, username, password, role, first name, last name, department, etc.).
*   **`incident` Table:** Stores incident details (incident ID, title, description, type, priority, status, employee name, technician id, technician name, creation date, resolution date, feedback, etc.).

## Getting Started

### Prerequisites

*   **Java Development Kit (JDK) 19 or higher:** Make sure a compatible JDK is installed.
*   **Maven:** Verify that Maven is installed and configured. Download from [Maven Website](https://maven.apache.org/download.cgi).
*   **Database System:** Install the MySQL database . Create a database and schema for your application.
*   **Database Credentials:** Gather database connection details (URL, username, password).

### Installation

1.  **Clone the Repository:**

    ```bash
    git clone [your_repository_url]
    cd Fixit
    ```

2. **Database Setup**:
    *   Execute the SQL scripts located in the `sql` directory to create the required tables in your database schema.

3.  **Configure Database Connection:**

    *   Navigate to the `BaseDAO.java` file located under `Fixit\src\main\java\com\fixit\Model\BaseDAO.java`.
    *   Update the database connection properties (URL, username, password) to match your local database setup.

4.  **Build the Project with Maven:**

    ```bash
    mvn clean install
    ```
5. **Run the project using Maven:**
   ```bash
   mvn javafx:run
   ```
   This command compiles the project and runs the application.

### Running the Application

*   The application should start with the login screen (Screen 1).
*   Use your credentials to log in with appropriate roles and access the system's features.

## Detailed Usage Guide

### Login Process
<img width="602" alt="login" src="https://github.com/user-attachments/assets/5f48b2a1-081c-4600-94f8-3452e20a7b1f" />

*   Launch the application.
*   Enter your username and password in the login screen.
*   The system will authenticate and redirect you to the appropriate dashboard based on your role (Admin, Employee, or Technician).

### Admin Interface Usage
<img width="714" alt="admin user management" src="https://github.com/user-attachments/assets/16096c7c-ea9a-4068-97b4-fed1622f5b94" />
<img width="761" alt="AdminReport" src="https://github.com/user-attachments/assets/2289cdd4-ac42-417e-8ce5-79868e243166" />

*   **User Management:** Create new users by filling the form and click "Create User" button, edit existing users by selecting the row and clicking "Edit" and delete users by selecting the row and clicking "Delete".
*  **View incidents:** Navigate through the report button to view all the incidents.
*   **Reporting:** Generate reports and export incident or user data using the corresponding buttons in the Admin Incident screen.
*   **Logout:** Use the "Logout" button to log out of the application.

### Employee Interface Usage
<img width="720" alt="UserDashboard" src="https://github.com/user-attachments/assets/cb3e732c-48af-4940-a1f2-92432f256b2b" />
<img width="717" alt="Feedback add" src="https://github.com/user-attachments/assets/9a830bb6-cfc6-4de8-9262-614054e0192f" />
<img width="716" alt="Feedback add sucess" src="https://github.com/user-attachments/assets/c06f930f-a393-4f92-aeed-f66907448408" />

*   **Incident Creation:** Use the "Incident Creation" panel to log new incidents, select priority and type and click the save button.
*  **Feedback:** Use the feedback input box to add feedback for resolved incidents.
*   **Incident Tracking:** Monitor the progress and status of your reported incidents in "My Incidents" table.
*   **Logout:** Log out using the "Logout" button on the top right corner.

### Technician Interface Usage
<img width="653" alt="TechDashboard" src="https://github.com/user-attachments/assets/5973f2aa-3c71-4061-a038-f85238f08e8e" />

*   **Incident Assignment:** View open incidents and assign them to yourself using the "Assign To Myself" button.
*   **Incident Resolution:** Manage the incidents assigned to you and mark them as resolved through the "Mark As Resolved" button.
*  **Incident Tracking:** View your open and resolved incidents in "My Incidents" table.
*   **Logout:** Log out using the "Logout" button on the top right corner.

