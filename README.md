Course Registration System (Course Management System)

A simple Java-based Course Management System that allows administrators to manage course details efficiently.
The application supports adding, viewing, updating, deleting, and searching courses using a menu-driven interface.

🚀 Features

➕ Add Course
Add a new course with:

Course ID

Course Title

Duration

Course Fee

📋 View Courses
Display all available courses in the system.

✏️ Update Course Fee
Modify the fee of an existing course using its Course ID.

❌ Delete Course
Remove a course from the system.

🔍 Search Course by Title
Find courses using the course title (case-insensitive search).

🛠️ Technologies Used

Java (Core Java, OOP concepts)

MySQL (for database storage)

JDBC (Java Database Connectivity)

Command Line Interface (CLI)

🗂️ Project Structure
Course-Registration-System/
│
├── CourseManagementSystem.java   # Main Java source file
├── CourseManagementSystem.class  # Compiled class file
├── course_db.sql                 # Database schema
├── README.md                     # Project documentation

🗄️ Database Details

Database Name: course_db

Table Name: courses

Table Structure:

courseId   INT PRIMARY KEY
title      VARCHAR(100)
duration   VARCHAR(50)
fee        DOUBLE

⚙️ How to Run the Project
1️⃣ Setup Database

Open MySQL

Create database and table using course_db.sql

2️⃣ Update JDBC Configuration

Update database credentials inside the Java file:

String url = "jdbc:mysql://localhost:3306/course_db";
String user = "your_mysql_username";
String password = "your_mysql_password";

3️⃣ Compile and Run
javac CourseManagementSystem.java
java CourseManagementSystem

📌 Sample Menu Output
1. Add Course
2. View Courses
3. Update Course Fee
4. Delete Course
5. Search Course by Title
6. Exit
Enter your choice:

🎯 Learning Outcomes

Understanding CRUD operations

Hands-on experience with JDBC

Applying OOP concepts in Java

Database interaction using SQL

Building real-world console applications

👨‍💻 Author

Lalit
Computer Science Student

