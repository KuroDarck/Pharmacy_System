package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Category;
import io.github.kurodarck.pharmacysystem.model.Product;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements CrudDAO<Product, String> {

    /**
     * Converts a database ResultSet row from #Products into a Product instance.
     */

    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setCode(resultSet.getString("code"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("unit_price"));
        product.setAmount(resultSet.getInt("product_quantity"));
        product.setIsActive(resultSet.getBoolean("is_active"));
        Timestamp createAt = resultSet.getTimestamp("created");
        Timestamp updateAt = resultSet.getTimestamp("updated");
        product.setCreatedAt(createAt != null ? createAt.toLocalDateTime() : null);
        product.setUpdatedAt(updateAt != null ? updateAt.toLocalDateTime() : null);

        int categoryId = resultSet.getInt("category_id");
        if (!resultSet.wasNull()) {
            product.setCategory(new Category(categoryId));
        }
        return product;
    }

    /**
     * Checks if an active #Product exists in the database by Code.
     * Returns true if found, false otherwise.
     */
    public boolean existsByCode(String code) throws SQLException {
        String query = "SELECT 1 FROM products WHERE code = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    /**
     * Saves a #Product record in the database and sets its generated ID.
     */
    @Override
    public Product create(Product entity) throws SQLException {
        String query = "INSERT INTO products (code,name,description,unit_price,product_quantity,is_active,created,updated,category_id)\n" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setBigDecimal(4, entity.getPrice());
            preparedStatement.setInt(5, entity.getAmount());
            preparedStatement.setBoolean(6, entity.getIsActive());
            LocalDateTime dateNow = LocalDateTime.now();
            preparedStatement.setTimestamp(7, Timestamp.valueOf(dateNow));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(dateNow));

            // Validación defensiva para evitar NullPointerException
            if (entity.getCategory() == null || entity.getCategory().getId() == null) {
                throw new IllegalArgumentException("El producto debe tener asignada una categoría válida.");
            }
            preparedStatement.setInt(9, entity.getCategory().getId());
            final int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }

                }
            }
            entity.setCreatedAt(dateNow);
            entity.setUpdatedAt(dateNow);
        }
        return entity;
    }

    @Override
    public boolean update(Product entity) throws SQLException {
        String query = "UPDATE products\n" + "SET code = ?, name = ?, description = ?, unit_price = ?, product_quantity = ?, is_active = ?, updated = ?,category_id = ?\n" + "WHERE id = ?";
        final int rowAffect;
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setBigDecimal(4, entity.getPrice());
            preparedStatement.setInt(5, entity.getAmount());
            preparedStatement.setBoolean(6, entity.getIsActive());
            LocalDateTime dateNow = LocalDateTime.now();
            preparedStatement.setTimestamp(7, Timestamp.valueOf(dateNow));

            // Validación defensiva para evitar NullPointerException
            if (entity.getCategory() == null || entity.getCategory().getId() == null) {
                throw new IllegalArgumentException("El producto debe tener asignada una categoría válida.");
            }
            preparedStatement.setInt(8, entity.getCategory().getId());
            preparedStatement.setInt(9, entity.getId());
            rowAffect = preparedStatement.executeUpdate();
            if (rowAffect > 0) {
                entity.setUpdatedAt(dateNow);
            }
        }
        return rowAffect > 0;
    }

    @Override
    public boolean softDelete(String code) throws SQLException {
        return false;
    }

    @Override
    public Optional<Product> findById(String code) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return List.of();
    }
}
