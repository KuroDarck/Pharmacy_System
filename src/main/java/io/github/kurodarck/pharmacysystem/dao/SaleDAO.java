package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import io.github.kurodarck.pharmacysystem.model.Customer;
import io.github.kurodarck.pharmacysystem.model.Employee;
import io.github.kurodarck.pharmacysystem.model.Sale;
import io.github.kurodarck.pharmacysystem.model.SaleDetail;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleDAO implements CrudDAO<Sale, Integer> {

    /**
     * Converts a database ResultSet row from #Sales into a sale instance.
     */
    private Sale mapResulSetToSale(ResultSet resultSet) throws SQLException {
        Sale sale = new Sale();
        Customer customer = new Customer();
        Employee employee = new Employee();
        sale.setId(resultSet.getInt("id"));
        customer.setId(resultSet.getInt("customer_id"));
        employee.setId(resultSet.getInt("employee_id"));
        sale.setCustomer(customer);
        sale.setEmployee(employee);
        sale.setTotal(resultSet.getBigDecimal("total"));
        Timestamp createAt = resultSet.getTimestamp("created");
        sale.setCreatedAt(createAt != null ? createAt.toLocalDateTime() : null);
        return sale;
    }

    /**
     * Checks if an existing #Sale exists in the database by Name.
     * Returns true if found, false otherwise.
     */
    private boolean exitsById(int id) throws SQLException {
        String query = " SELECT 1 FROM sales WHERE id = ?";
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * Saves an #Sale record in the database and sets its generated ID.
     */
    @Override
    public Optional<Sale> create(Sale entity) throws SQLException {
        String query = "INSERT INTO sales (customer_id, employee_id,invoice_number, total,created) VALUES (?,?,?, ?,?)";
        final int rowAffect;
        Customer customer = entity.getCustomer();
        Employee employee = entity.getEmployee();

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.setString(3, entity.getInvoiceNumber());
            preparedStatement.setBigDecimal(4, entity.getTotal());
            LocalDateTime createToday = LocalDateTime.now();
            preparedStatement.setTimestamp(5, Timestamp.valueOf(createToday));
            rowAffect = preparedStatement.executeUpdate();
            if (rowAffect > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }

            }
            entity.setCreatedAt(createToday);
        }
        return Optional.of(entity);
    }


    /**
     * This is Overloading method, that It's going to be used for SaleService
     * @param entity
     * @param connection
     * @return {@link Sale}
     * @throws SQLException
     */
    public Optional<Sale> create(Sale entity, Connection connection) throws SQLException {
        String query = "INSERT INTO sales (customer_id, employee_id,invoice_number, total,created) VALUES (?,?,?, ?,?)";
        final int rowAffect;
        Customer customer = entity.getCustomer();
        Employee employee = entity.getEmployee();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.setString(3, entity.getInvoiceNumber());
            preparedStatement.setBigDecimal(4, entity.getTotal());
            LocalDateTime createToday = LocalDateTime.now();
            preparedStatement.setTimestamp(5, Timestamp.valueOf(createToday));
            rowAffect = preparedStatement.executeUpdate();
            if (rowAffect > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }

            }
            entity.setCreatedAt(createToday);
        }
        return Optional.of(entity);
    }

    /**
     * Updates a #sale in the database by ID.
     * Returns true if a row was modified, false otherwise.
     * Well you're not allowed to updated sales /figures in the real life, can't you?
     */
    @Override
    public boolean update(Sale entity) throws SQLException {
        return false;
    }

    /**
     * Deactivates a #Sale in the database by ID (soft delete).
     * Returns true if a row was modified, false otherwise.
     * Ok, we have the same here, It isn't allowed to deled figures or sales.
     */
    @Override
    public boolean softDelete(Integer dni) throws SQLException {
        return false;
    }

    /**
     * Retrieves a #Sale from the database by ID.
     * Returns an Optional with the Sale if found, or empty otherwise.
     */
    @Override
    public Optional<Sale> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM sales where id=?";

        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResulSetToSale(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all #Sales from the database.
     * Returns a list of Sales, or an empty list if none exist.
     */
    @Override
    public List<Sale> findAll() throws SQLException {
        String query = "SELECT * FROM sales";
        List<Sale> saleList = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    saleList.add(mapResulSetToSale(resultSet));
                }
            }
        }
        return saleList;
    }
}
