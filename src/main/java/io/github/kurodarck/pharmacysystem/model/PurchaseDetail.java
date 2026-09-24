package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;

public class PurchaseDetail extends TransactionDetail {
    private Purchase purchase;


    public PurchaseDetail () {
        super();
    }
    public PurchaseDetail(int id, Product product, int amount, BigDecimal price, BigDecimal subtotal,Purchase purchase) {
        super(id, product, amount, price, subtotal);
        this.purchase = purchase;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "PurchaseDetail{" +
                "purchase=" + purchase +
                "} " + super.toString();
    }
}
