package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Product;
import io.github.kurodarck.pharmacysystem.model.Purchase;
import io.github.kurodarck.pharmacysystem.model.PurchaseDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PurchaseDetailDAO implements CrudDAO<PurchaseDetail, Integer> {

    /**
     * A function to mapping the resultset
     *
     * @param resultSet receive this variable to mapping the resultset to PurchaseDetail
     * @return {@link PurchaseDetail} this is the resultset completely mapped into an object
     * @throws SQLException In case of something goes wrong
     */
    public PurchaseDetail mapResultSetToPurchaseDetail(ResultSet resultSet) throws SQLException {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        Purchase purchase = new Purchase();
        Product product = new Product();

        purchaseDetail.setId(resultSet.getInt("id"));
        purchaseDetail.setPrice(resultSet.getBigDecimal("purchase_price"));
        purchaseDetail.setAmount(resultSet.getInt("purchase_amount"));
        purchaseDetail.setSubtotal(resultSet.getBigDecimal("purchase_subtotal"));
        purchase.setId(resultSet.getInt("purchase_id"));
        purchaseDetail.setProduct(product);
        product.setId(resultSet.getInt("product_id"));
        purchaseDetail.setProduct(product);

        return purchaseDetail;
    }

    /**
     * this method is used to create PurchaseDetail row in the Database
     *
     * @param entity we're gonna use this variable as a PurchaseDetail Object
     * @return {@link Optional}  we use this type of variable because of Crud implementation
     * @throws SQLException In case of something goes wrong with the Database Connection
     */
    @Override
    public Optional<PurchaseDetail> create(PurchaseDetail entity) throws SQLException {
        String query = "INSERT  INTO purchase_details (purchase_price,purchase_amount,purchase_subtotal,purchase_id,product_id) VALUES (?,?,?,?,?)";
        final int rowAffect;
        Purchase purchase = entity.getPurchase();
        Product product = entity.getProduct();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setBigDecimal(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getAmount());
            preparedStatement.setBigDecimal(3, entity.getSubtotal());
            preparedStatement.setInt(4, purchase.getId());
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
     * This is an Overloading method to use with the PurchaseService
     *
     * @param entity     this represents our future object
     * @param connection this is the connection that we're gonna use
     * @return {@link Optional} we use this variable to  follow the crud implementation
     * @throws SQLException in case of something goes wrong with the database
     */

    public Optional<PurchaseDetail> create(PurchaseDetail entity, Connection connection) throws SQLException {

        String query = "INSERT  INTO purchase_details (purchase_price,purchase_amount,purchase_subtotal,purchase_id,product_id) VALUES (?,?,?,?,?)";
        final int rowAffect;
        Purchase purchase = entity.getPurchase();
        Product product = entity.getProduct();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setBigDecimal(1, entity.getPrice());
            preparedStatement.setInt(2, entity.getAmount());
            preparedStatement.setBigDecimal(3, entity.getSubtotal());
            preparedStatement.setInt(4, purchase.getId());
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
     * This method isn't gonna be developed to mantean a clearly and solid data management
     *
     * @param entity
     * @return {@link Boolean}
     * @throws SQLException
     */
    @Override
    public boolean update(PurchaseDetail entity) throws SQLException {
        return false;
    }

    /**
     * This method isn't gonna be developed to mantean a clearly and solid data management
     *
     * @param id
     * @return {@link Boolean}
     * @throws SQLException
     */
    @Override
    public boolean softDelete(Integer id) throws SQLException {
        return false;
    }

    /**
     * this method is gonna be used in case that we want to find this object in the Database
     *
     * @param id this is the parameter tha we're gonna use to find the object
     * @return {@link Optional} in case tha we find the object
     * @throws SQLException in case tha something goes wrong
     */
    @Override
    public Optional<PurchaseDetail> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM purchase_details WHERE id=?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToPurchaseDetail(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    /**
     * Well here, we just list the table
     * @return {@link List} The complete table mapped
     * @throws SQLException in case of something goes wrong
     */
    @Override
    public List<PurchaseDetail> findAll() throws SQLException {
        String query = "SELECT * FROM purchase_details";
        List<PurchaseDetail> purchaseDetails = new ArrayList<>();
        try(Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    purchaseDetails.add(mapResultSetToPurchaseDetail(resultSet));
                }
            }

        }

        return purchaseDetails;
    }
}
