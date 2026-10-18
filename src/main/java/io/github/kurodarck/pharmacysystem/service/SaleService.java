package io.github.kurodarck.pharmacysystem.service;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
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

    public SaleService() {

    }

    public SaleService(SaleDAO saleDAO, SaleDetailDAO saleDetailDao) {
        this.saleDAO = saleDAO;
        this.saleDetailDAO = saleDetailDao;
    }

    public void registerSale(Sale sale, List<SaleDetail> saleDetailList) throws SQLException {

        Connection connection = MySQLConnection.getConnection();
        connection.setAutoCommit(false);
        try {
            Sale newSale = new Sale();
            Optional<Sale> optionalSale = saleDAO.create(sale, connection);
            newSale.setId(optionalSale.get().getId());
            for (SaleDetail saleDetail : saleDetailList) {
                saleDetail.setSale(newSale);
                saleDetailDAO.create(saleDetail, connection);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }


    }
}
