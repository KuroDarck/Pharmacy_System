package io.github.kurodarck.pharmacysystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Purchase extends Transaction {
    private Supplier supplier;

    public Purchase() {
        super();
    }
    public Purchase(int id, String invoiceNumber, Employee employee,Supplier supplier, BigDecimal total, LocalDateTime createdAt ) {
        super(id,invoiceNumber,employee,total,createdAt);
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "supplier=" + supplier +
                "} " + super.toString();
    }
}
