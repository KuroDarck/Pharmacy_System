package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Employee;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements CrudDAO<Employee, String> {


    /**
     * Converts a database ResultSet row into an Employee instance.
     */
    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setDni(resultSet.getString("dni"));
        employee.setFullName(resultSet.getString("full_name"));
        employee.setUserName(resultSet.getString("username"));
        employee.setAddress(resultSet.getString("address"));
        employee.setTelephoneNumber(resultSet.getString("telephone"));
        employee.setEmail(resultSet.getString("email"));
        employee.setPassword(resultSet.getString("password"));
        employee.setRole(resultSet.getString("rol"));
        employee.setActive(resultSet.getBoolean("is_active"));
        Timestamp createdAt = resultSet.getTimestamp("created");
        Timestamp updatedAt = resultSet.getTimestamp("updated");

        if (createdAt != null) {
            employee.setCreatedAt(createdAt.toLocalDateTime());

        } else {
            employee.setCreatedAt(null);

        }

        if (updatedAt != null) {
            employee.setUpdatedAt(updatedAt.toLocalDateTime());
        } else {
            employee.setUpdatedAt(null);
        }

        return employee;

    }


    /**
     * Authenticates an employee by username and password.
     * Returns an Optional containing the Employee if found and active, or empty otherwise.
     */

    public Optional<Employee> loggingQuery(String username, String user_Password) throws SQLException{
        String query = "SELECT * FROM employees WHERE username = ? AND password = ? AND is_active = true";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, user_Password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEmployee(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if an active employee exists in the database by DNI.
     * Returns true if found, false otherwise.
     */
    public boolean existsByDni(String dni) throws SQLException {
        String query = "SELECT 1 FROM employees WHERE dni = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, dni);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    /**
     * Saves an employee record in the database and sets its generated ID.
     */
    @Override
    public Employee create(Employee entity) throws SQLException {
        String query = "INSERT INTO employees (dni, full_name, username, address, telephone, email, password, rol, is_active, created, updated) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getDni());
            preparedStatement.setString(2, entity.getFullName());
            preparedStatement.setString(3, entity.getUserName());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setString(5, entity.getTelephoneNumber());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getPassword());
            preparedStatement.setString(8, entity.getRole());
            preparedStatement.setBoolean(9, entity.isActive());
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(10, Timestamp.valueOf(nowDate));
            preparedStatement.setTimestamp(11, Timestamp.valueOf(nowDate));

            final int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
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
     * Updates an employee in the database by DNI.
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean update(Employee entity) throws SQLException {
        final int rowAffected;
        String query = "UPDATE employees \n" +
                "SET dni = ?, full_name = ?, username = ?, address = ?, telephone = ?, email = ?, password = ?, rol = ?, is_active = ?, updated = ? \n" +
                "WHERE dni = ?";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getDni());
            preparedStatement.setString(2, entity.getFullName());
            preparedStatement.setString(3, entity.getUserName());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setString(5, entity.getTelephoneNumber());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getPassword());
            preparedStatement.setString(8, entity.getRole());
            preparedStatement.setBoolean(9, entity.isActive());
            LocalDateTime nowDate = LocalDateTime.now();
            preparedStatement.setTimestamp(10, Timestamp.valueOf(nowDate));
            preparedStatement.setString(11, entity.getDni());

            rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                entity.setUpdatedAt(nowDate);
            }

        }
        return rowAffected > 0;
    }

    /**
     * Deactivates an employee in the database by DNI (soft delete).
     * Returns true if a row was modified, false otherwise.
     */
    @Override
    public boolean softDelete(String dni) throws SQLException {
        final int rowAffected;
        String query = "UPDATE employees \n" +
                "SET is_active = false, updated = ? \n" +
                "WHERE dni = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            LocalDateTime dateNow = LocalDateTime.now();
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dateNow));
            preparedStatement.setString(2, dni);
            rowAffected = preparedStatement.executeUpdate();
        }
        return rowAffected > 0;
    }

    /**
     * Retrieves an active employee from the database by DNI.
     * Returns an Optional with the employee if found, or empty otherwise.
     */
    @Override
    public Optional<Employee> findById(String dni) throws SQLException {

        String query = "SELECT * FROM employees WHERE dni = ? AND is_active = true";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEmployee(resultSet));

                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all active employees from the database.
     * Returns a list of active employees, or an empty list if none exist.
     */
    @Override
    public List<Employee> findAll() throws SQLException {
        String query = "SELECT * FROM employees WHERE is_active = true";
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(mapResultSetToEmployee(resultSet));
                }

            }
        }
        return employees;
    }
}

