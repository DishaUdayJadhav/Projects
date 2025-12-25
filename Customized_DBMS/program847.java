/*
/////////////////////////////////////////////////////////////////
 Project Title : Customized DBMS (Console Based)
 Language      : Java
 Description   : This project simulates a simple DBMS using
                 Java Collections and Serialization.
 Author        : Disha Uday Jadhav                 
 Features      :
   1. Insert employee record
   2. Display all records
   3. Update employee record
   4. Count total records
   5. Sort records by salary
   6. Filter records by salary range
   7. Delete record
   8. Backup & Restore (Serialization)
   9. Export data to text file
////////////////////////////////////////////////////////////////
*/

import java.util.*;
import java.io.*;

// ================= Employee Class =================
class Employee implements Serializable
{
    private static final long serialVersionUID = 1L;

    public int empId;
    public String name;
    public int age;
    public String address;
    public int salary;

    private static int counter = 1;

    public Employee(String name, int age, String address, int salary)
    {
        this.empId = counter++;
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public static void setCounter(int value)
    {
        counter = value;
    }

    public String toString()
    {
        return "ID : " + empId +
               " | Name : " + name +
               " | Age : " + age +
               " | Address : " + address +
               " | Salary : " + salary;
    }
}

// ================= DBMS Class =================
class MarvellousDBMS implements Serializable
{
    private static final long serialVersionUID = 1L;

    private LinkedList<Employee> table;

    public MarvellousDBMS()
    {
        System.out.println("DBMS started successfully...");
        table = new LinkedList<>();
    }

    // Insert record
    public void insert(String name, int age, String address, int salary)
    {
        table.add(new Employee(name, age, address, salary));
        System.out.println("Record inserted successfully");
    }

    // Display all records
    public void selectAll()
    {
        if(table.isEmpty())
        {
            System.out.println("No records found");
            return;
        }

        for(Employee e : table)
        {
            System.out.println(e);
        }
    }

    // Update record
    public void updateRecord(int id, int newSalary)
    {
        for(Employee e : table)
        {
            if(e.empId == id)
            {
                e.salary = newSalary;
                System.out.println("Record updated successfully");
                return;
            }
        }
        System.out.println("Record not found");
    }

    // Count records
    public void countRecords()
    {
        System.out.println("Total records : " + table.size());
    }

    // Sort by salary
    public void sortBySalary()
    {
        table.sort(Comparator.comparingInt(e -> e.salary));
        System.out.println("Records sorted by salary");
    }

    // Salary range filter
    public void salaryRange(int min, int max)
    {
        boolean found = false;
        for(Employee e : table)
        {
            if(e.salary >= min && e.salary <= max)
            {
                System.out.println(e);
                found = true;
            }
        }
        if(!found)
        {
            System.out.println("No records in this salary range");
        }
    }

    // Delete record
    public void deleteById(int id)
    {
        Iterator<Employee> it = table.iterator();
        while(it.hasNext())
        {
            if(it.next().empId == id)
            {
                it.remove();
                System.out.println("Record deleted successfully");
                return;
            }
        }
        System.out.println("Record not found");
    }

    // Export to file
    public void exportToFile(String fileName)
    {
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName)))
        {
            for(Employee e : table)
            {
                pw.println(e);
            }
            System.out.println("Data exported to file successfully");
        }
        catch(Exception e)
        {
            System.out.println("Export failed");
        }
    }

    // Backup
    public void takeBackup(String path)
    {
        try(ObjectOutputStream oos =
            new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(this);
            System.out.println("Backup taken successfully");
        }
        catch(Exception e)
        {
            System.out.println("Backup failed");
        }
    }

    // Restore
    public static MarvellousDBMS restoreBackup(String path)
    {
        try(ObjectInputStream ois =
            new ObjectInputStream(new FileInputStream(path)))
        {
            MarvellousDBMS obj = (MarvellousDBMS) ois.readObject();

            int maxId = 0;
            for(Employee e : obj.table)
            {
                if(e.empId > maxId)
                    maxId = e.empId;
            }
            Employee.setCounter(maxId + 1);

            return obj;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}

// ================= Main Class =================
class program847
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        MarvellousDBMS db =
            MarvellousDBMS.restoreBackup("MarvellousDBMS.ser");

        if(db == null)
            db = new MarvellousDBMS();

        int choice = 0;

        while(choice != 20)
        {
            System.out.println("\n1.Insert  2.Display  3.Update");
            System.out.println("4.Count   5.Sort    6.Salary Range");
            System.out.println("7.Delete  8.Export  9.Backup");
            System.out.println("20.Exit");

            choice = sc.nextInt();
            sc.nextLine();

            switch(choice)
            {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Address: ");
                    String address = sc.nextLine();
                    System.out.print("Salary: ");
                    int salary = sc.nextInt();
                    db.insert(name, age, address, salary);
                    break;

                case 2:
                    db.selectAll();
                    break;

                case 3:
                    System.out.print("Emp ID: ");
                    int id = sc.nextInt();
                    System.out.print("New Salary: ");
                    int newSalary = sc.nextInt();
                    db.updateRecord(id, newSalary);
                    break;

                case 4:
                    db.countRecords();
                    break;

                case 5:
                    db.sortBySalary();
                    break;

                case 6:
                    System.out.print("Min Salary: ");
                    int min = sc.nextInt();
                    System.out.print("Max Salary: ");
                    int max = sc.nextInt();
                    db.salaryRange(min, max);
                    break;

                case 7:
                    System.out.print("Emp ID: ");
                    db.deleteById(sc.nextInt());
                    break;

                case 8:
                    db.exportToFile("EmployeeData.txt");
                    break;

                case 9:
                    db.takeBackup("MarvellousDBMS.ser");
                    break;

                case 20:
                    db.takeBackup("MarvellousDBMS.ser");
                    System.out.println("Thank you for using DBMS");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
        sc.close();
    }
}
