package ticketoffice.persistence.dao.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Generics interface for basic CRUD operations and close method.
 *
 * @param <T> -   type of models in db table
 */
public interface AbstractDao<T> extends AutoCloseable {

    int create(T entity);

    Optional<T> getById(int id);

    boolean delete(int id);

    boolean update(T entity);

    List<T> getAll();

    /**
     * Closes(returns to Poll) connection.
     */
    void close();
}
