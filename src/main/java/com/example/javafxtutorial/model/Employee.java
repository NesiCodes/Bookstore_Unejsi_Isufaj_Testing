package com.example.javafxtutorial.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Employee implements Serializable {

    private String userId;

    private String name;

    private String birthday;

    private String phone;

    private String email;

    private int salary;

    private int accessLevel;

    private String userName;

    private String password;

    private transient Role role;

    public Employee(String name, String birthday, String phone, String email, int salary, int accessLevel, String userName, String password) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.accessLevel = accessLevel;
        this.userName = userName;
        this.password = password;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", accessLevel=" + accessLevel +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
