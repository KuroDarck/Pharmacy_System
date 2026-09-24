package io.github.kurodarck.pharmacysystem.model;

import java.time.LocalDateTime;

public class Employee extends Person {
    private String userName;
    private String password;
    private String role;



    public Employee() {
        super();
    }

    public Employee(int id, String dni, String fullName, String address, String email, String username, String password, String role, String telephoneNumber, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, dni, fullName, address, telephoneNumber, email, isActive, createdAt,updatedAt);
        this.userName = username;
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                "} " + super.toString();
    }
}
