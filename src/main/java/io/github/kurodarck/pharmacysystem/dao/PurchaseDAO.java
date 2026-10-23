package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PurchaseDAO implements CrudDAO<Purchase, Integer> {

    /**
     * Converts a database ResultSet row from #Purchse into a Category instance.
     */


    private Purchase mapResultSetToPurchase(ResultSet resultSet) throws SQLException {
        Purchase purchase = new Purchase();
        Employee employee = new Employee();
        Supplier supplier = new Supplier();
        purchase.setId(resultSet.getInt("id"));
        supplier.setId(resultSet.getInt("supplier_id"));
        employee.setId(resultSet.getInt("employee_id"));
        purchase.setSupplier(supplier);
        purchase.setEmployee(employee);
        purchase.setInvoiceNumber(resultSet.getString("invoice_number"));
        Timestamp purchaseDate = resultSet.getTimestamp("created");
        purchase.setCreatedAt(purchaseDate != null ? purchaseDate.toLocalDateTime() : null);
        purchase.setTotal(resultSet.getBigDecimal("total"));
        return purchase;
    }

    /**
     * Checks if an active #Purchase exists in the database by id.
     * Returns true if found, false otherwise.
     */
    private boolean existsById(int id) throws SQLException {
        String query = "SELECT 1 FROM purchases WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * This is our Create method implementation
     * @param entity this is the object that we receive
     * @return {@link Optional} use to return the object mapped or an empty object
     * @throws SQLException  in case tha we have problem with the database
     */
    @Override
    public Optional<Purchase> create(Purchase entity) throws SQLException {
        String query = "INSERT INTO purchases (supplier_id,employee_id,invoice_number,total,created) VALUES (?,?,?,?,?)";
        Supplier supplier= entity.getSupplier();
        Employee employee = entity.getEmployee();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, supplier.getId());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.setString(3, entity.getInvoiceNumber());
            preparedStatement.setBigDecimal(4, entity.getTotal());
            LocalDateTime purchaseDate = LocalDateTime.now();
            preparedStatement.setTimestamp(5, Timestamp.valueOf(purchaseDate));
            final int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
            }
            entity.setCreatedAt(purchaseDate);
        }

        return Optional.of(entity);
    }

    /**
     * thid is a overloading method to use with the PurchaseService
     * @param entity the object tha we receive
     * @return {@link Optional} A complete object mapped or an empty object
     * @throws SQLException in case of something goes wrong
     */

    public Optional<Purchase> create(Purchase entity, Connection connection) throws SQLException {
        String query = "INSERT INTO purchases (supplier_id,employee_id,invoice_number,total,created) VALUES (?,?,?,?,?)";
        Supplier supplier= entity.getSupplier();
        Employee employee = entity.getEmployee();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, supplier.getId());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.setString(3, entity.getInvoiceNumber());
            preparedStatement.setBigDecimal(4, entity.getTotal());
            LocalDateTime purchaseDate = LocalDateTime.now();
            preparedStatement.setTimestamp(5, Timestamp.valueOf(purchaseDate));
            final int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
            }
            entity.setCreatedAt(purchaseDate);
        }

        return Optional.of(entity);
    }

    /**
     * Updates a #Purchase in the database by ID.
     * Returns true if a row was modified, false otherwise.
     * In this case this method it's going to be created but never developed or used, because in an invoice.
     * we can't be allowed to make changes
     */
    @Override
    public boolean update(Purchase entity) throws SQLException {
        return false;
    }

    /**
     * Deactivates a #Purchase in the database by ID (soft delete).
     * Returns true if a row was modified, false otherwise.
     * ok let me clarify this you can't delete anything here, because you don't want that the system crash it.
     */
    @Override
    public boolean softDelete(Integer dni) throws SQLException {
        return false;
    }

    /**
     * Retrieves an existing #Purchase from the database by ID.
     * Returns an Optional with the Purchase if found, or empty otherwise.
     */
    @Override
    public Optional<Purchase> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM purchases WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToPurchase(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all existing #Purchase from the database.
     * Returns a list of existing Purchase, or an empty list if none exist.
     */
    @Override
    public List<Purchase> findAll() throws SQLException {
        String query = "SELECT * FROM purchases";
        List<Purchase> purchaseList = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    purchaseList.add(mapResultSetToPurchase(resultSet));
                }
            }
        }
        return purchaseList;
    }
}
