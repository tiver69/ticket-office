package ticketoffice.persistence.mapper;

import ticketoffice.persistence.dao.interfaces.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Mapper<T> {
    void setDao(AbstractDao abstractDao);

    T extractItem(ResultSet resultSet) throws SQLException;

    ArrayList<T> extractItemList(ResultSet resultSet) throws SQLException;
}
