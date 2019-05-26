package ticketoffice.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedParameters {
    void setParameters(PreparedStatement statement) throws SQLException;
}
