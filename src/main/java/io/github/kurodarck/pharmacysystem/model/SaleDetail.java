package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;

public class SaleDetail extends TransactionDetail {
    private Sale sale;
    private Product product;

    public SaleDetail() {
        super();
    }

    public SaleDetail(Integer id) {
        super(id);
    }

    public SaleDetail(int id, Product product, Sale sale, int amount, BigDecimal price, BigDecimal subtotal) {
        super(id, product, amount, price, subtotal);
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "SaleDetail{" +
                "sale=" + sale +
                ", product=" + product +
                "} " + super.toString();
    }
}
