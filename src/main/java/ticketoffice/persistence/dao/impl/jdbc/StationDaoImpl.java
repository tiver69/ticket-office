package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.mapper.StationMapper;
import ticketoffice.persistence.mapper.TicketMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class StationDaoImpl extends AbstractDaoImpl<Station> implements StationDao {

    private String INSERT = "INSERT INTO stations (id,name) VALUES (?,?)";
    private String GET_BY_ID = "SELECT * FROM stations WHERE id = ?";
    private String GET_ALL = "SELECT * FROM stations ORDER BY name";
    private String DELETE = "DELETE FROM stations WHERE id=?";
    private String UPDATE = "UPDATE stations SET name=? WHERE id=?";


    public StationDaoImpl(Connection connection) {
        super(connection, new StationMapper());
        LOG = Logger.getLogger(StationDaoImpl.class);
    }

    @Override
    public int create(Station station) {
        return create(INSERT, statement -> {
            statement.setInt(1, station.getId());
            statement.setString(2, station.getName());
        });
    }

    @Override
    public Optional<Station> getById(int id) {
        return getById(GET_BY_ID, statement -> {
            statement.setInt(1, id);
        });
    }

    @Override
    public boolean delete(int id) {
        return delete(DELETE, statement -> {
            statement.setInt(1, id);
        });
    }

    @Override
    public boolean update(Station station) {
        return update(UPDATE, statement -> {
            statement.setString(1, station.getName());
            statement.setInt(7, station.getId());
        });
    }

    @Override
    public List<Station> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }
}
