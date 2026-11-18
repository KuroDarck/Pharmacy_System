package io.github.kurodarck.pharmacysystem.service;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.dao.ProductDAO;
import io.github.kurodarck.pharmacysystem.dao.SaleDAO;
import io.github.kurodarck.pharmacysystem.dao.SaleDetailDAO;
import io.github.kurodarck.pharmacysystem.model.Sale;
import io.github.kurodarck.pharmacysystem.model.SaleDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SaleService {
    private SaleDAO saleDAO = new SaleDAO();
    private SaleDetailDAO saleDetailDAO = new SaleDetailDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public SaleService() {
    }

    public SaleService(SaleDAO saleDAO, SaleDetailDAO saleDetailDao) {
        this.saleDAO = saleDAO;
        this.saleDetailDAO = saleDetailDao;
    }

    /**
     * This service, Manage the Sale and every single detail
     *
     * @param sale           receive a sale Headline
     * @param saleDetailList and every single detail that the customer buy it
     * @throws SQLException in case of something goes wrong
     */
    public void registerSale(Sale sale, List<SaleDetail> saleDetailList) throws SQLException {

        int productID;
        int soldQuantity;
        boolean productUpdated;

        Connection connection = null;

        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false);

            Sale newSale = new Sale();
            Optional<Sale> optionalSale = saleDAO.create(sale, connection);

            optionalSale.ifPresent(value -> newSale.setId(value.getId()));

            for (SaleDetail saleDetail : saleDetailList) {
                saleDetail.setSale(newSale);
                saleDetailDAO.create(saleDetail, connection);
                productID = saleDetail.getProduct().getId();
                soldQuantity = saleDetail.getAmount();
                productUpdated = productDAO.updateStockSele(productID, soldQuantity, connection);
                if (!productUpdated) {
                    throw new SQLException("Error: No se pudo actualizar el stock del producto ID: " + productID);
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
