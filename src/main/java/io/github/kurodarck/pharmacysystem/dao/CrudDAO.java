package io.github.kurodarck.pharmacysystem.dao;

import io.github.kurodarck.pharmacysystem.model.Sale;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, DI> {

    /** It's created in this way, because we're going to use in every single class to create a data set,
     * and we don't want to have incompatibility with the return parameter
     */
    Optional<T> create(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;

    boolean softDelete(DI id) throws SQLException;

    Optional<T> findById(DI id) throws SQLException;

    List<T> findAll() throws SQLException;


}
