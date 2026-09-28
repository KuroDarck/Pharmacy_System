package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Category;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAO implements CrudDAO<Category, Integer> {


    /**
     * Converts a database ResultSet row from #Cateries into a Category instance.
     */
    private Category mapResultSetToCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        category.setIsActive(resultSet.getBoolean("is_active"));
        Timestamp createdAt = resultSet.getTimestamp("created");
        Timestamp updatedAt = resultSet.getTimestamp("updated");
        category.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        category.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);

        return category;
    }

    /**
     * Checks if an active #Category exists in the database by Name.
     * Returns true if found, false otherwise.
     */
    public boolean existsByName(String name) throws SQLException {
        String query = "SELECT 1 FROM categories WHERE name = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * Saves an #Category record in the database and sets its generated ID.
     */
    @Override
    public Category create(Category entity) throws SQLException {
        String query = "INSERT INTO categories (name, is_active, created, updated) \n" +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.getIsActive());
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(3, Timestamp.valueOf(nowDate));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(nowDate));
            final int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow > 0) {
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
     * Updates a #Category in the database by ID.
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean update(Category entity) throws SQLException {
        final int affectedRows;
        String query = "UPDATE categories \n" + "SET name = ?, is_active = ?, updated = ? \n" + "WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.getIsActive());
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(3, Timestamp.valueOf(nowDate));
            preparedStatement.setInt(4, entity.getId());
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                entity.setUpdatedAt(nowDate);
            }
        }
        return affectedRows > 0;
    }

    /**
     * Deactivates an #Category in the database by ID (soft delete).
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean softDelete(Integer id) throws SQLException {
        final int affectedRows;
        String query = "UPDATE categories \n" + "SET is_active = false, updated = ? \n" + "WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(1, Timestamp.valueOf(nowDate));
            preparedStatement.setInt(2, id);
            affectedRows = preparedStatement.executeUpdate();
        }
        return affectedRows > 0;
    }

    /**
     * Retrieves an active #Category from the database by ID.
     * Returns an Optional with the Category if found, or empty otherwise.
     */
    @Override
    public Optional<Category> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM categories WHERE id = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToCategory(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all active #Categories from the database.
     * Returns a list of active Category, or an empty list if none exist.
     */
    @Override
    public List<Category> findAll() throws SQLException {
        String query = "SELECT * FROM categories WHERE is_active = true";
        List<Category> categories = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(mapResultSetToCategory(resultSet));
                }
            }
        }
        return categories;
    }
}
