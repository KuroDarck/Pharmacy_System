package io.github.kurodarck.pharmacysystem.model;

import java.time.LocalDateTime;

public class Customer extends Person {
    public Customer() {
        super();
    }
    public Customer(int id, String dni, String fullName, String address, String telephoneNumber , String email,boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt){
        super(id, dni, fullName, address, telephoneNumber, email,isActive , createdAt, updatedAt);
    }


    @Override
    public String toString() {
        return getFullName();
    }
}
