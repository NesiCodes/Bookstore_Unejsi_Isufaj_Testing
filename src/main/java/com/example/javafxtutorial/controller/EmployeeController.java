package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Fixed serialVersionUID

    private ArrayList<Employee> employees;
    private final String filePath = "src/main/resources/com/example/javafxtutorial/database/employees.dat";

    public EmployeeController() {
        employees = new ArrayList<>();
        readEmployeesFromFile();
    }

    public void readEmployeesFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            employees = (ArrayList<Employee>) ois.readObject();
            System.out.println("Employees read from file: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeEmployeesToFile() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/employees.dat"))) {
            oos.writeObject(employees);
            System.out.println("Employees written to file: " + "src/main/resources/com/example/javafxtutorial/database/employees.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeEmployeesToFile(List<Employee> employees) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/employees.dat"))) {
            oos.writeObject(employees);
            System.out.println("Employees written to file: " + "src/main/resources/com/example/javafxtutorial/database/employees.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public void printEmployees(){
        for(Employee e: employees){
            System.out.println(e);
        }
    }

    public Employee login(String userName, String password) {
        for (Employee employee : employees) {
            if (employee.getUserName().equals(userName) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    //testing
    public static void main(String[] args) {
        EmployeeController employeeController = new EmployeeController();

        employeeController.printEmployees();


        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Unejsi", "6 Jan 2003", "02203051", "unejsi@gmail.com", 4000, 3, "unejsi", "unejsi1"));
        employees.add(new Employee("Dea", "26 Jun 2004", "05616051", "dea@gmail.com", 3000, 2, "dea", "dea1"));
        employees.add(new Employee("Redion", "20 Jun 2004", "12516123", "redion@gmail.com", 2000, 1, "redion", "redion1"));

        employeeController.writeEmployeesToFile(employees);
    }
    private void printEmployeesFileContent(String message) {
        System.out.println(message);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/com/example/javafxtutorial/database/employees.dat"))) {
            ArrayList<Employee> employees = (ArrayList<Employee>) ois.readObject();
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------");
    }
}

