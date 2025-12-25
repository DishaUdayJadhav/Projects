# StudyMate - MVC Architecture

- A comprehensive GUI-based study tracking application built with Java Swing and MySQL     database following the Model-View-Controller (MVC) architectural pattern.
- Helps students systematically log,track,summarize,add,delete,serach,edit thier study activities.
- This project demonstrates practical usage of Java collections,File I/O and Object-Oriented design in a real world,utility driven apllication.


## Features

### Core Functionality
- **Add Study Logs**      : Create new study entries with subject, duration, and description
- **View All Logs**       : Display all study logs in a sortable table format
- **Edit Logs**           : Modify existing study log entries
- **Delete Logs**         : Remove study log entries with confirmation
- **Search Functionality**: Real-time search through subjects, descriptions, and dates
- **Export to CSV**       : Export study data to CSV format with file dialog
- **Summary Reports**     : Generate summaries by date and by subject
- **Data Persistence**    : All data stored in MySQL database

### Technical Features
- **MVC Architecture**: Clean separation of concerns with Model, View, and Controller
- **MySQL Database**  : Robust data storage with proper SQL queries
- **Professional GUI**: Modern Swing interface with menus, toolbars, and dialogs
- **Input Validation**: Comprehensive form validation with user feedback
- **Table Sorting**   : Sortable columns for better data organization
- **Real-time Search**: Instant search results as you type
- **Error Handling**  : Proper exception handling with user-friendly messages

## Project Structure

```
src/
├── model/
│   └── StudyLog.java               # Data model class
├── view/
│   ├── StudyTrackerView.java       # Main GUI window
│   └── StudyLogDialog.java         # Add/Edit dialog
├── controller/
│   └── StudyTrackerController.java # Business logic controller
├── database/
│   ├── DatabaseConnection.java     # Database connection manager
│   └── StudyLogDAO.java            # Data Access Object
├── utils/
│   └── CSVExporter.java            # CSV export utility
└── StudyTrackerApp.java            # Main application entry point
```

## Prerequisites

### Software Requirements
1. **Java Development Kit (JDK)** - Version 8 or higher
2. **XAMPP** - For MySQL database server
3. **MySQL JDBC Driver** - For database connectivity

### Database Setup
1. **Start XAMPP Control Panel**
2. **Start Apache and MySQL services**
3. The application will automatically create the database and table

## Installation & Setup

### Step 1: Download MySQL JDBC Driver
1. Go to [MySQL Connector/J Downloads](https://dev.mysql.com/downloads/connector/j/)
2. Download the latest `mysql-connector-java-X.X.XX.jar` file
3. Place this JAR file in your project root directory

### Step 2: Start XAMPP Services
1. Open XAMPP Control Panel
2. Start **Apache** service
3. Start **MySQL** service
4. Verify MySQL is running on port 3306

### Step 3: Compile the Application
1. Open Command Prompt in your project directory
2. Create a directory for compiled classes:
mkdir bin
3. Compile all Java files:
javac -cp "mysql-connector-j-9.3.0.jar" -d bin src\model\*.java src\view\*.java src\controller\*.java src\database\*.java src\utils\*.java StudyTrackerApp.java

4. Replace mysql-connector-j-9.3.0.jar with the actual filename if different.

### Step 4: Run the Application
java -cp "bin;mysql-connector-j-9.3.0.jar" StudyTrackerApp -cp ".:mysql-connector-java-8.0.33.jar" StudyTrackerApp
```

## Database Configuration

### Default Settings
- **Host**: localhost
- **Port**: 3306
- **Database**: study_tracker (auto-created)
- **Username**: root
- **Password**: (empty)

### Custom Configuration
To modify database settings, edit `src/database/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/study_tracker";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

### Database Schema
The application automatically creates the following table:

```sql
CREATE TABLE study_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    subject VARCHAR(255) NOT NULL,
    duration DECIMAL(5,2) NOT NULL,
    description TEXT
);
```

## Usage Guide

### Main Interface
1. **Menu Bar**: File, Edit, and View menus with all major functions
2. **Toolbar**: Quick access buttons for common operations
3. **Search Bar**: Real-time search functionality
4. **Data Table**: Sortable table displaying all study logs
5. **Summary Panel**: Display reports by date or subject

### Adding Study Logs
1. Click **"Add"** button or use **File → Add Study Log**
2. Fill in the form:
   - **Subject**: Name of the subject studied
   - **Duration**: Time spent in hours (decimal allowed)
   - **Description**: Additional notes about the study session
3. Click **"Save"** to store the log

### Searching Logs
1. Type in the search field at the top
2. Results filter automatically as you type
3. Search works across subjects, descriptions, and dates
4. Click **"Clear"** to reset search

### Editing/Deleting Logs
1. Select a row in the table
2. Click **"Edit"** to modify or **"Delete"** to remove
3. Confirm deletion when prompted

### Generating Reports
1. Use **"Summary by Date"** to see daily study totals
2. Use **"Summary by Subject"** to see subject-wise totals
3. Reports appear in the right panel

### Exporting Data
1. Click **"Export CSV"** button
2. Choose save location in the file dialog
3. Data exports with proper CSV formatting

## Troubleshooting

### Common Issues

#### 1. "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution**: Ensure MySQL JDBC JAR is in your classpath

#### 2. "Communications link failure"
**Solution**: 
- Check if XAMPP MySQL service is running
- Verify MySQL is running on port 3306
- Check Windows Firewall settings

#### 3. "Access denied for user 'root'"
**Solution**: 
- Check MySQL username/password in DatabaseConnection.java
- Reset MySQL root password if needed

#### 4. "javac/java is not recognized"
**Solution**: Add Java to your system PATH environment variable

#### 5. Application doesn't start
**Solution**:
- Ensure all Java files compiled without errors
- Check MySQL JDBC JAR is correct version
- Verify XAMPP services are running

### Performance Tips
1. **Regular Database Maintenance**: Periodically backup your data
2. **Search Optimization**: Use specific search terms for faster results
3. **Memory Management**: Restart application if handling large datasets

## Development

### MVC Architecture Benefits
- **Model**: Clean data representation and business rules
- **View**: Separated UI logic for better maintainability  
- **Controller**: Centralized business logic and data flow control

### Extending the Application
1. **Add New Features**: Extend controller and update view
2. **Database Changes**: Modify DAO classes and update schema
3. **UI Improvements**: Update view classes and add new dialogs

### Code Style
- Follow Java naming conventions
- Use proper exception handling
- Document complex business logic
- Maintain separation of concerns


## Support

For technical support or questions:
1. Check the troubleshooting section
2. Verify all prerequisites are met
3. Ensure XAMPP services are running
4. Contact your instructor for additional help

---

**Version**: 2.0  
**Last Updated** : 2025  
**Architecture** : MVC Pattern  
**Database**     : MySQL  
**GUI Framework**: Java Swing




### Author      : Disha Uday Jadhav