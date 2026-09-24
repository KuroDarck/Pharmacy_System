package io.github.kurodarck.pharmacysystem.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, DI> {
    T create(T entity) throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean softDelete(DI dni) throws SQLException;

    Optional<T> findById(DI dni) throws SQLException;

    List<T> findAll() throws SQLException;


}
