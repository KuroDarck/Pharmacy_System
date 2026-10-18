package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Sale extends Transaction {
    private Customer customer;
    private Product product;

    public Sale() {
        super();
    }

    public Sale(Integer id) {
        super(id);
    }

    public Sale(int id, String invoiceNumber, Employee employee, Customer customer, BigDecimal total, LocalDateTime createdAt) {
        super(id, invoiceNumber, employee, total, createdAt);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "customer=" + customer +
                ", product=" + product +
                "} " + super.toString();
    }
}
