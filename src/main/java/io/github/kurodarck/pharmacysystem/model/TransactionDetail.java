package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDetail {

    private int id;
    private Product product;
    private int amount;
    private BigDecimal price;
    private BigDecimal subtotal;

    public TransactionDetail() {}

    public TransactionDetail(int id, Product product, int amount, BigDecimal price, BigDecimal subtotal) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.subtotal = subtotal;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                ", price=" + price +
                ", subtotal=" + subtotal +
                '}';
    }
}