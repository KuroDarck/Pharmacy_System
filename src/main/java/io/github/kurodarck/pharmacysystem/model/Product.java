package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product extends Item {
    private String code;
    private BigDecimal price;
    private int amount;
    private String description;
    private Category category;
    public Product(int id, String code, String name, BigDecimal price, int amount, String description, Category category, boolean isActive,LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id,name,isActive, createdAt,updatedAt);
        this.code = code;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }
    public Product(){
        super();
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category=" + category +
                "} " + super.toString();
    }
}
