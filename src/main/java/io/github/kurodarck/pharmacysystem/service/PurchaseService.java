package io.github.kurodarck.pharmacysystem.service;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.dao.ProductDAO;
import io.github.kurodarck.pharmacysystem.dao.PurchaseDAO;
import io.github.kurodarck.pharmacysystem.dao.PurchaseDetailDAO;
import io.github.kurodarck.pharmacysystem.model.Purchase;
import io.github.kurodarck.pharmacysystem.model.PurchaseDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PurchaseService {

    private PurchaseDAO purchaseDAO;
    private PurchaseDetailDAO purchaseDetailDAO;
    private ProductDAO productDAO;

    public PurchaseService() {
    }

    public PurchaseService(PurchaseDAO purchaseDAO, PurchaseDetailDAO purchaseDetailDAO) {
        this.purchaseDAO = purchaseDAO;
        this.purchaseDetailDAO = purchaseDetailDAO;
    }

    public void registerPurchase(Purchase purchase, List<PurchaseDetail> purchaseDetails) throws SQLException {

        int idProduct;
        int quantityBuyIt;
        boolean productUpdated;

        Connection connection = null;
        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);

            Purchase newPurchase = new Purchase(); //This variable it's gonna use to manage the Purchase ID

            Optional<Purchase> optionalPurchase = purchaseDAO.create(purchase, connection);
            optionalPurchase.ifPresent(value -> newPurchase.setId(value.getId()));

            for (PurchaseDetail purchaseDetail : purchaseDetails) {
                purchaseDetail.setPurchase(newPurchase);
                purchaseDetailDAO.create(purchaseDetail, connection);

                idProduct = purchaseDetail.getProduct().getId();
                quantityBuyIt = purchaseDetail.getAmount();
                productUpdated = productDAO.updateStockPurchase(idProduct, quantityBuyIt, connection);
                if (!productUpdated) {
                    throw new SQLException("Error: No se pudo actualizar el stock del producto ID: " + idProduct);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }


    }


}
