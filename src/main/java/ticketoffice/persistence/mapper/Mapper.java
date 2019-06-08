package ticketoffice.persistence.mapper;

import ticketoffice.persistence.dao.interfaces.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface Mapper<T> {
    void setDao(AbstractDao abstractDao);

    Optional<T> extractItem(ResultSet resultSet) throws SQLException;

    ArrayList<T> extractItemList(ResultSet resultSet) throws SQLException;
}
