package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.model.Sale;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SaleDAO implements CrudDAO<Sale,String> {
    @Override
    public Sale create(Sale entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Sale entity) throws SQLException {
        return false;
    }

    @Override
    public boolean softDelete(String dni) throws SQLException {
        return false;
    }

    @Override
    public Optional<Sale> findById(String dni) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Sale> findAll() throws SQLException {
        return List.of();
    }
}
