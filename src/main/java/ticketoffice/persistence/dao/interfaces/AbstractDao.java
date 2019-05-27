package ticketoffice.persistence.dao.interfaces;

import java.util.List;

public interface AbstractDao<T> extends AutoCloseable {

    int create(T entity);

    T getById(int id);

    boolean delete(int id);

    boolean update(T entity);

    List<T> getAll();

    void close();
}
