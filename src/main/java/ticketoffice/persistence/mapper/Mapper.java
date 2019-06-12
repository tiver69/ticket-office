package ticketoffice.persistence.mapper;

import ticketoffice.persistence.dao.interfaces.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Interface for mapper classes, for extraction T item from db result set
 *
 * @param <T> -   specified type of extracted item
 */
public interface Mapper<T> {
    /**
     * Set current using Dao with its connection, for additional request to db
     * while mapping list of items
     *
     * @param abstractDao
     */
    void setDao(AbstractDao abstractDao);

    /**
     * Extract single item from resultSet
     *
     * @param resultSet
     * @return Optional of T type, is item was successfully extracted, Optional of Nullable if
     * result set was empty.
     */
    Optional<T> extractItem(ResultSet resultSet) throws SQLException;

    /**
     * Extract number of items from resultSet, additionally requesting mapping each of item
     *
     * @param resultSet
     * @return ArrayList of T type
     * result set was empty.
     */
    ArrayList<T> extractItemList(ResultSet resultSet) throws SQLException;
}
