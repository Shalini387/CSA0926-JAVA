# CO5 – Application Based Implementation

## Job Portal / Recruitment Management System

### Project Overview

The Job Portal / Recruitment Management System is a Java-based application developed using Java, JDBC, and MySQL.

The system is designed to help an HR recruitment team manage job openings and candidate applications. It allows jobs to be added, candidates to apply for available jobs, application statuses to be updated, and application information to be searched and reported.

---

## Objectives

- To manage job openings efficiently.
- To store and manage candidate applications.
- To validate job details before accepting applications.
- To update the current status of applications.
- To search applications using Job ID and status.
- To generate reports using SQL queries and JOIN operations.
- To connect a Java application with a MySQL database using JDBC.

---

## Features

- Add new job openings.
- Automatically generate Job IDs.
- Submit candidate applications.
- Automatically generate Application IDs.
- Validate Job ID before submitting an application.
- Prevent applications for non-existent jobs.
- Update application status.
- Search applications using Job ID.
- Search applications based on application status.
- Display job and candidate details using SQL JOIN.
- Generate a shortlisted candidate report.
- Handle database errors using exception handling.

---

## Technologies Used

- Programming Language: Java
- Database: MySQL
- Database Connectivity: JDBC
- GUI: Java Swing
- IDE: IntelliJ IDEA

---

## Database

The application uses a MySQL database named `job_portal`.

### Jobs Table

The `jobs` table stores information about available job openings.

Fields include:

- `job_id`
- `title`
- `department`

### Applications Table

The `applications` table stores information about candidates who apply for jobs.

Fields include:

- `app_id`
- `job_id`
- `candidate_name`
- `status`

The `job_id` in the applications table is related to the corresponding job in the jobs table.

---

## Application Operations

### 1. Add Job

Allows the HR team to add a new job by entering:

- Job Title
- Department

### 2. Apply for Job

Allows a candidate to submit an application by entering:

- Job ID
- Candidate Name

The application validates whether the entered Job ID exists before inserting the application.

### 3. Update Application Status

Allows the application status to be updated.

Example statuses include:

- Applied
- Shortlisted
- Rejected
- Selected

### 4. Search Applications

Applications can be searched using:

- Job ID
- Application Status

### 5. Shortlisted Candidate Report

The system generates a report showing job titles and candidates whose applications have been shortlisted.

---

## Validation and Error Handling

The application performs validation before database operations.

For example:

- A Job ID is checked before an application is inserted.
- An application cannot be submitted for a non-existent job.
- SQL exceptions are handled using `try-catch`.
- Appropriate error messages are displayed to the user.

---

## SQL JOIN

The project uses SQL JOIN operations to combine information from the `jobs` and `applications` tables.

This allows the system to display related information such as:

- Job Title
- Department
- Candidate Name
- Application Status

---

## Testing

The application was tested for the following scenarios:

- Adding a new job successfully.
- Submitting an application for a valid Job ID.
- Rejecting an application for an invalid Job ID.
- Updating application status.
- Searching applications using Job ID.
- Searching applications using application status.
- Generating shortlisted candidate reports.
- Handling database errors.

---

## Project Structure

```text
CO5_Application_Based_Implementation/
│
├── README.md
│
└── JobPortal/
    │
    ├── JobPortal.iml
    │
    └── src/
        └── JobPortalApp.java


How to Run
Install Java and MySQL.
Create the job_portal database in MySQL.
Create the required jobs and applications tables.
Open the project in IntelliJ IDEA.
Add the MySQL JDBC connector to the project.
Update the database connection details in JobPortalApp.java.
Run JobPortalApp.java.
Use the application interface to manage jobs and candidate applications.
Author

Shalini Reddy
