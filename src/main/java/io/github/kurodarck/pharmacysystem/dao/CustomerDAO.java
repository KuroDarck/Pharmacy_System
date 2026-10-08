package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO implements CrudDAO<Customer, String> {

    /**
     * Converts a database ResultSet row from #Customer into an Employee instance.
     */
    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setDni(resultSet.getString("dni"));
        customer.setFullName(resultSet.getString("full_Name"));
        customer.setAddress(resultSet.getString("address"));
        customer.setTelephoneNumber(resultSet.getString("telephone"));
        customer.setEmail(resultSet.getString("email"));
        customer.setIsActive(resultSet.getBoolean("is_Active"));
        Timestamp createdAt = resultSet.getTimestamp("created");
        Timestamp updatedAt = resultSet.getTimestamp("updated");
        customer.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        customer.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);

        return customer;

    }

    /**
     * Checks if an active #Customer exists in the database by DNI.
     * Returns true if found, false otherwise.
     */
    public boolean existsByDni(String dni) throws SQLException {
        String query = "SELECT * FROM customers WHERE dni = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * Saves a #Customer record in the database and sets its generated ID.
     */
    @Override
    public Customer create(Customer entity) throws SQLException {
        String query = "INSERT INTO customers (dni, full_name, address, telephone, email, is_active, created, updated) \n" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getDni());
            preparedStatement.setString(2, entity.getFullName());
            preparedStatement.setString(3, entity.getAddress());
            preparedStatement.setString(4, entity.getTelephoneNumber());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setBoolean(6, entity.getIsActive());
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(7, Timestamp.valueOf(nowDate));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(nowDate));

            final int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getInt(1));
                    }
                }
            }
            entity.setCreatedAt(nowDate);
            entity.setUpdatedAt(nowDate);
        }
        return entity;
    }

    /**
     * Updates a #Customer in the database by DNI.
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean update(Customer entity) throws SQLException {
        final int affectedRows;
        String query = "UPDATE customers \n" + "SET dni = ?, full_name = ?, address = ?, telephone = ?, email = ?, is_active = ?, updated = ? \n" + "WHERE dni = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getDni());
            preparedStatement.setString(2, entity.getFullName());
            preparedStatement.setString(3, entity.getAddress());
            preparedStatement.setString(4, entity.getTelephoneNumber());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setBoolean(6, entity.getIsActive());
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(7, Timestamp.valueOf(nowDate));
            preparedStatement.setString(8, entity.getDni());

            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                entity.setUpdatedAt(nowDate);
            }


        }

        return affectedRows > 0;
    }

    /**
     * Deactivates an #Customer in the database by DNI (soft delete).
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean softDelete(String dni) throws SQLException {
        final int affectedRows;
        String query = "UPDATE customers \n" + "SET is_active = false, updated = ? \n" + "WHERE dni = ?";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, dni);
            affectedRows = preparedStatement.executeUpdate();
        }
        return affectedRows > 0;
    }

    /**
     * Finds an active customer by their DNI, returning an {@link Optional} with the result if found.
     */
    @Override
    public Optional<Customer> findById(String dni) throws SQLException {
        String query = "SELECT * FROM customers WHERE dni = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToCustomer(resultSet));

                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all active #Customer from the database.
     * Returns a list of active #Customer, or an empty list if none exist.
     */
    @Override
    public List<Customer> findAll() throws SQLException {
        String query = "SELECT * FROM customers WHERE is_active = true";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    customers.add(mapResultSetToCustomer(resultSet));
                }
            }
        }
        return customers;

    }
}
