# Customized DBMS (Java)

## 📌 Project Overview
This project is a **console-based Customized Database Management System (DBMS)** developed using **Core Java**.  
It simulates basic DBMS functionalities such as inserting, updating, deleting, querying, and storing employee records using Java Collections and Serialization.

The project demonstrates how database concepts can be implemented without using any external database.

---

## 🛠️ Technologies Used
- Java (Core Java)
- Java Collections Framework
- File Handling
- Serialization
- VS Code (IDE)

---

## ✨ Features / Functionalities
1. Insert employee record  
2. Display all employee records  
3. Update employee salary  
4. Count total records  
5. Sort records by salary  
6. Filter employees by salary range  
7. Delete employee record  
8. Export data to text file  
9. Backup and restore data using serialization  

---

## 📂 Project Structure
Customized-DBMS/
│
├── program847.java
├── MarvellousDBMS.ser (generated after backup)
├── EmployeeData.txt (generated after export)
└── README.md


---

## ▶️ How to Run the Project

### Step 1: Compile the program
javac program847.java

### Step 2: Run the program
java program847

⌨️ Sample Menu
1.Insert   2.Display   3.Update
4.Count    5.Sort      6.Salary Range
7.Delete   8.Export    9.Backup
20.Exit


Insert Employee

Input

1
Amit
21
Pune
25000


Output

Record inserted successfully

Display Employees

Input

2


Output

ID : 1 | Name : Amit | Age : 21 | Address : Pune | Salary : 25000

Update Salary

Input

3
1
30000


Output

Record updated successfully

Backup & Restore

Backup file created: MarvellousDBMS.ser

Data is automatically restored when program runs again

### Data Persistence

This project uses Java Serialization to store and restore employee records, ensuring data is not lost after program termination.

### Learning Outcomes

Understanding DBMS concepts

Implementing CRUD operations

Using Java Collections as in-memory database

Implementing serialization for data persistence

Writing menu-driven console applications



### Future Enhancements

Role-based access (Admin/User)

Indexing using HashMap

Export to CSV format

Advanced query parsing

GUI-based interface

### Author

Disha Jadhav
Computer Science Student
