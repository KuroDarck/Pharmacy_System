package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Product;
import io.github.kurodarck.pharmacysystem.model.Sale;
import io.github.kurodarck.pharmacysystem.model.SaleDetail;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleDetailDAO implements CrudDAO<SaleDetail, Integer> {

    /**
     * Converts a database ResultSet row from #SalesDetail into a sale instance.
     */
    private SaleDetail mapResultSetToSalesDetail(ResultSet resultSet) throws SQLException {
        SaleDetail saleDetail = new SaleDetail();
        Sale sale = new Sale();
        Product product = new Product();
        saleDetail.setId(resultSet.getInt("id"));
        saleDetail.setPrice(resultSet.getBigDecimal("sale_price"));
        saleDetail.setAmount(resultSet.getInt("sale_amount"));
        saleDetail.setSubtotal(resultSet.getBigDecimal("sale_subtotal"));
        product.setId(resultSet.getInt("product_id"));
        saleDetail.setProduct(product);
        sale.setId(resultSet.getInt("sale_id"));
        saleDetail.setSale(sale);

        return saleDetail;
    }


    @Override
    public Optional<SaleDetail> create(SaleDetail entity) throws SQLException {
        Sale sale = entity.getSale();
        Product product = entity.getProduct();
        String query = "INSERT INTO sale_details (sale_price,sale_amount,sale_subtotal,sale_id,product_id) VALUES (?, ?, ?, ?,?)";
        final int rowAffect;

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getAmount());
            preparedStatement.setBigDecimal(3, entity.getSubtotal());
            preparedStatement.setInt(4, sale.getId());
            preparedStatement.setInt(5, product.getId());
            rowAffect = preparedStatement.executeUpdate();
            if (rowAffect > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getInt(1));
                    }
                }
            }
        }
        return Optional.of(entity);
    }

    /**
     * This is Overloading method
     * @param entity this is the object that we're gonna use
     * @param connection we receive an active connection
     * @return {@link SaleDetail}
     * @throws SQLException
     */
    public Optional<SaleDetail> create(SaleDetail entity,Connection connection) throws SQLException {
        Sale sale = entity.getSale();
        Product product = entity.getProduct();
        String query = "INSERT INTO sale_details (sale_price,sale_amount,sale_subtotal,sale_id,product_id) VALUES (?, ?, ?, ?,?)";
        final int rowAffect;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getAmount());
            preparedStatement.setBigDecimal(3, entity.getSubtotal());
            preparedStatement.setInt(4, sale.getId());
            preparedStatement.setInt(5, product.getId());
            rowAffect = preparedStatement.executeUpdate();
            if (rowAffect > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return Optional.of(entity);
    }
    /**
     * Well, I'm not going to develop this because we can't update invoice details,
     * without destroy our Database relation.
     */
    @Override
    public boolean update(SaleDetail entity) throws SQLException {
        return false;
    }

    /**
     * Same here, I'm not going to develop this because we can't delete invoice details
     */
    @Override
    public boolean softDelete(Integer dni) throws SQLException {
        return false;
    }

    /**
     * Retrieves a sale detail by its unique identifier.
     *
     * @param id the unique identifier of the sale detail to retrieve
     * @return an Optional containing the SaleDetail if found, or an empty Optional otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Optional<SaleDetail> findById(Integer id) throws SQLException {
        String query = "Select * from sale_details where id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToSalesDetail(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves a list of SaleDetail from the database.
     * @return a List of SaleDetail objects
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<SaleDetail> findAll() throws SQLException {
        String query = "Select * from sale_details";
        List<SaleDetail> saleDetails = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    saleDetails.add(mapResultSetToSalesDetail(resultSet));
                }
            }
        }
        return saleDetails;
    }


}
