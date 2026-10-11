package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Supplier;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierDAO implements CrudDAO<Supplier, Integer> {

    /**
     * Converts a database ResultSet row from #Supplier into a Supplier instance.
     */
    private Supplier mapResulsetToSupplier(ResultSet resultSet) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(resultSet.getInt("id"));
        supplier.setDni(resultSet.getString("dni"));
        supplier.setFullName(resultSet.getString("name"));
        supplier.setDescription(resultSet.getString("description"));
        supplier.setTelephoneNumber(resultSet.getString("telephone"));
        supplier.setAddress(resultSet.getString("address"));
        supplier.setEmail(resultSet.getString("email"));
        supplier.setCity(resultSet.getString("city"));
        supplier.setIsActive(resultSet.getBoolean("isActive"));
        Timestamp createdAt = resultSet.getTimestamp("created");
        Timestamp updatedAt = resultSet.getTimestamp("updated");
        supplier.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        supplier.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);
        return supplier;
    }


    /**
     * Checks if an active #Supplier exists in the database by dni.
     * Returns true if found, false otherwise.
     */
    private boolean existsByDni(String dni) throws SQLException {
        String query = "SELECT 1 FROM suppliers WHERE dni = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * Saves an #Supplier record in the database and sets its generated ID.
     */
    @Override
    public Supplier create(Supplier entity) throws SQLException {
        String query = "INSERT INTO suppliers (dni,name,description,telephone,address,email,city,is_active, created, updated) \n" +
                "VALUES (?, ?, ?, ?,?, ?, ?, ?,?,?)";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getDni());
            preparedStatement.setString(2, entity.getFullName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setString(4, entity.getTelephoneNumber());
            preparedStatement.setString(5, entity.getAddress());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getCity());
            preparedStatement.setBoolean(8, entity.getIsActive());
            LocalDateTime dateNow = LocalDateTime.now();
            preparedStatement.setTimestamp(9, Timestamp.valueOf(dateNow));
            preparedStatement.setTimestamp(10, Timestamp.valueOf(dateNow));
            final int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getInt(1));
                    }
                }
            }
            entity.setCreatedAt(dateNow);
            entity.setUpdatedAt(dateNow);
        }
        return entity;
    }

    /**
     * Updates a #Supplier in the database by ID.
     * Returns true if a row was modified, false otherwise.
     * But let me be honest this only update name and update date and if this supplier is active
     */
    @Override
    public boolean update(Supplier entity) throws SQLException {
        final int affectedRows;
        String query = "UPDATE suppliers SET name = ?, is_active = ?, updated = ? WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getFullName());
            preparedStatement.setBoolean(2, entity.getIsActive());
            LocalDateTime dateNow = LocalDateTime.now();
            preparedStatement.setTimestamp(3, Timestamp.valueOf(dateNow));
            preparedStatement.setInt(4, entity.getId());
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                entity.setUpdatedAt(dateNow);
            }

        }
        return affectedRows > 0;
    }

    /**
     * Deactivates an #Supplier in the database by ID (soft delete).
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean softDelete(Integer id) throws SQLException {
        final int affectedRows;
        String query = "UPDATE suppliers SET is_active = false, updated =?  WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            LocalDateTime dateNow = LocalDateTime.now();
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dateNow));
            preparedStatement.setInt(2, id);
            affectedRows = preparedStatement.executeUpdate();
        }
        return affectedRows > 0;
    }

    /**
     * Retrieves an active #Supplier from the database by ID.
     * Returns an Optional with the Supplier if found, or empty otherwise.
     */
    @Override
    public Optional<Supplier> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM suppliers WHERE id = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResulsetToSupplier(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all active #Supplier from the database.
     * Returns a list of active Supplier, or an empty list if none exist.
     */
    @Override
    public List<Supplier> findAll() throws SQLException {
        String query = "SELECT * FROM suppliers WHERE is_active = true";
        List<Supplier> supplier = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
           try (ResultSet resultSet = preparedStatement.executeQuery()) {
               while (resultSet.next()) {
                   supplier.add(mapResulsetToSupplier(resultSet));
               }
           }
        }
        return supplier;
    }
}
