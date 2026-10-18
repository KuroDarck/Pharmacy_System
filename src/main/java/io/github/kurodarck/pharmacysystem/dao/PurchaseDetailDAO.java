package io.github.kurodarck.pharmacysystem.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PurchaseDetailDAO  implements CrudDAO{
    /**
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public Optional create(Object entity) throws SQLException {
        return Optional.empty();
    }

    /**
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(Object entity) throws SQLException {
        return false;
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public boolean softDelete(Object id) throws SQLException {
        return false;
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Optional findById(Object id) throws SQLException {
        return Optional.empty();
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public List findAll() throws SQLException {
        return List.of();
    }
}
