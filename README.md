## Problem Statement
The objective of this project is to design and develop an Urban Waste Collection and Citizen Complaint Tracking System using Java (JDBC + OOP).
The system enables the municipality to:
  1. Manage Citizens: maintain household details with ward and route mapping.
  2. Record Scheduled Waste Collection Visits: log vehicle, crew, route, date, and shift.
  3. Register Complaints: allow citizens to raise waste collection or sanitation-related complaints.
  4. Track Complaint Status: update complaint progress (Open, In-Progress, Resolved, Closed).
  5. Maintain Waste Service Records: store all actions (scheduled visits, complaints) in a unified table.
  6. Perform Validations: prevent duplicate citizens, invalid complaint dates, bad inputs.
  7. Handle Exceptions: custom exceptions for validation errors, invalid status updates, and unresolved complaints.
  8. Provide Simple Console-Based User Interface: interactive menu for all operations.
This project applies Object-Oriented Programming, DAO pattern, Service Layer architecture, and Oracle JDBC database management.

## How to Run in Eclipse IDE

Step 1: Open Eclipse IDE
Download and install from: https://www.eclipse.org/downloads/

Step 2: Create a New Java Project
Go to:
File → New → Java Project
Enter Project Name:Urban-Waste-And-Citizen-Complaints
Click Finish.

Step 3: Create Required Packages
Right-click on src → New → Package
Create the following:
com.waste.bean, com.waste.dao, com.waste.service, com.waste.exception, com.waste.util, com.waste.main

Step 4: Add Your Classes
Inside each package, create the required .java files:
 1. Citizen.java
 2. WasteServiceRow.java
 3. CitizenDAO.java
 4. WasteServiceDAO.java
 5. WasteService.java
 6. DBUtil.java
 7. ValidationException.java
 8. ComplaintStatusException.java
 9. ActiveComplaintsExistException.java
 10. WasteMain.java (Main class)
     
Step 5: Add Oracle JDBC Driver
Download ojdbc8.jar or ojdbc11.jar
Then:
Right-click your project → Build Path →
Add External Archives → choose the JAR.

Step 6: Run the Application
Go to: com.waste.main → WasteMain.java
Right-click → Run As → Java Application

## Database Tables Used
CITIZEN_TBL
WASTE_SERVICE_TBL
SERVICE_ROW_SEQ

## Output:
<img width="934" height="325" alt="Screenshot 2026-01-30 225336" src="https://github.com/user-attachments/assets/8c904b26-91f5-45f2-9a0d-ab41eb03302c" />

<img width="1523" height="262" alt="Screenshot 2026-02-02 114957" src="https://github.com/user-attachments/assets/08b978ea-aa84-489f-bd24-eae066e205dc" />

## Student Details:
 Name: PRIYADHARSHINI M
 Roll No: 717823P141

