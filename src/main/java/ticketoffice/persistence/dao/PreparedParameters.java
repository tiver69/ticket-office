package ticketoffice.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * FunctionalInterface for passing passing all parameters to sql-query
 */
@FunctionalInterface
public interface PreparedParameters {
    void setParameters(PreparedStatement statement) throws SQLException;
}
