package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.model.Purchase;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PurchaseDAO implements CrudDAO<Purchase, Integer> {

    @Override
    public Purchase create(Purchase entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Purchase entity) throws SQLException {
        return false;
    }

    @Override
    public boolean softDelete(Integer dni) throws SQLException {
        return false;
    }

    @Override
    public Optional<Purchase> findById(Integer dni) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Purchase> findAll() throws SQLException {
        return List.of();
    }
}
