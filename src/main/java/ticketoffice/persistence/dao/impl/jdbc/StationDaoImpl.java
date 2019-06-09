package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.Station;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.mapper.StationMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class StationDaoImpl extends AbstractDaoImpl<Station> implements StationDao {

    private String locale = "ru";

    private String INSERT = "INSERT INTO stations (id,name_en,name_ru) VALUES (?,?,?)";
    private String GET_BY_ID = "SELECT * FROM stations WHERE id = ?";
    private String GET_ALL = "SELECT * FROM stations ORDER BY name_" + locale;
    private String DELETE = "DELETE FROM stations WHERE id=?";
    private String UPDATE = "UPDATE stations SET name_" + locale + "=? WHERE id=?";


    public StationDaoImpl(Connection connection) {
        super(connection, new StationMapper());
        setLocale(locale);
        LOG = Logger.getLogger(StationDaoImpl.class);
    }

    @Override
    public void setLocale(String locale) {
        this.locale = locale;
        StationMapper stationMapper = new StationMapper();
        stationMapper.setLocale(locale);
        super.setMapper(stationMapper);
    }

    @Override
    public int create(Station station) {
        return create(INSERT, statement -> {
            statement.setInt(1, station.getId());
            statement.setString(2, station.getName());
            statement.setString(3, station.getName());
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
