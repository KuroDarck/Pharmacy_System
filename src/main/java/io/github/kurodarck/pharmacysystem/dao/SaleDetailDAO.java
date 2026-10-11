package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.model.Supplier;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SaleDetailDAO implements CrudDAO<Supplier, Integer> {
    @Override
    public Supplier create(Supplier entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return false;
    }

    @Override
    public boolean softDelete(Integer dni) throws SQLException {
        return false;
    }

    @Override
    public Optional<Supplier> findById(Integer dni) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Supplier> findAll() throws SQLException {
        return List.of();
    }
}
