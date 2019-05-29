package ticketoffice.persistence.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> extends AutoCloseable {

    int create(T entity);

    Optional<T> getById(int id);

    boolean delete(int id);

    boolean update(T entity);

    List<T> getAll();

    void close();
}
