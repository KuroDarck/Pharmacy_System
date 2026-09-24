package io.github.kurodarck.pharmacysystem.model;

import java.time.LocalDateTime;

public class Supplier extends Person {
    private String description;
    private String city;

    public Supplier() {
        super();
    }

    public Supplier(int id, String dni, String fullName, String description, String address, String telephoneNumber, String email, String city, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, dni, fullName, address, telephoneNumber, email,isActive,createdAt,updatedAt);
        this.description = description;
        this.city = city;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "description='" + description + '\'' +
                ", city='" + city + '\'' +
                "} " + super.toString();
    }
}
