package io.github.kurodarck.pharmacysystem.model;

import java.time.LocalDateTime;

public class Category extends Item {

    public Category() {
        super();
    }

    public Category(int id, String name, LocalDateTime createdAt, boolean isActive, LocalDateTime updatedAt) {
        super(id, name,isActive, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return getName();
    }
}
