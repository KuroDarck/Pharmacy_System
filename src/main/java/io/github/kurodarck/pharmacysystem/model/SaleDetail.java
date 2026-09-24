package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;

public class SaleDetail extends TransactionDetail {
    private Sale sale;

    public SaleDetail(){
        super();
    }
    public SaleDetail(int id, Product product, int amount, BigDecimal price, BigDecimal subtotal,Sale sale) {
        super(id, product, amount, price, subtotal);
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }
    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "SaleDetail{" +
                "sale=" + sale +
                "} " + super.toString();
    }
}
