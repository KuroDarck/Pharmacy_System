package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements  CrudDAO<Product,String> {
    @Override
    public Product create(Product entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Product entity) throws SQLException {
        return false;
    }

    @Override
    public boolean softDelete(String dni) throws SQLException {
        return false;
    }

    @Override
    public Optional<Product> findById(String dni) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return List.of();
    }
}
