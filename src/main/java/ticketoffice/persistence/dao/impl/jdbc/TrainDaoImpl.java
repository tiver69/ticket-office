package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.interfaces.TrainDao;
import ticketoffice.persistence.mapper.TrainMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrainDaoImpl extends AbstractDaoImpl<Train> implements TrainDao {

    private String INSERT = "INSERT INTO trains (id,frequency,markup) VALUES (?,?,?)";
    private String GET_ALL = "SELECT * FROM trains";
    private String GET_BY_ID = "SELECT * FROM trains WHERE id = ?";
    private String DELETE = "DELETE FROM trains WHERE id=?";
    private String UPDATE = "UPDATE trains SET frequency=?, markup=? WHERE id=?";

    private String GET_FROM_STATION_TO_STATION = "Select departure.train_id as id, departure.order as departure_order, arrival.order as arrival_order " +
            "from train_stations as departure, train_stations as arrival " +
            "where departure.station_id = ? and arrival.station_id = ? and departure.train_id=arrival.train_id " +
            "and departure.order < arrival.order;";

    public TrainDaoImpl(Connection connection) {
        super(connection, new TrainMapper());
        LOG = Logger.getLogger(TrainDaoImpl.class);
    }

    @Override
    public int create(Train train) {
        return create(INSERT, statement -> {
            statement.setInt(1, train.getId());
            statement.setInt(2, train.getFrequency());
            statement.setInt(3, train.getMarkup());
        });
    }

    @Override
    public Optional<Train> getById(int id) {
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
    public boolean update(Train train) {
        return update(UPDATE, statement -> {
            statement.setInt(1, train.getFrequency());
            statement.setInt(2, train.getMarkup());
            statement.setInt(3, train.getId());
        });
    }

    @Override
    public List<Train> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }

    @Override
    public List<Train> getTrainInDirection(int departureStationId, int destinationStationId) {
        return getAll(GET_FROM_STATION_TO_STATION, statement -> {
            statement.setInt(1, departureStationId);
            statement.setInt(2, destinationStationId);
        });
    }
}
