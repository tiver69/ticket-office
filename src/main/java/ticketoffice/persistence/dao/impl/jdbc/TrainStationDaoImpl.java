package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.Station;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;
import ticketoffice.persistence.mapper.StationMapper;
import ticketoffice.persistence.mapper.TrainStationMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrainStationDaoImpl extends AbstractDaoImpl<TrainStation> implements TrainStationDao {

    private String INSERT = "INSERT INTO train_stations (station_id, train_id, order, prise, arrival, departure)" +
            " VALUES (?,?,?,?,?,?)";
    private String GET_BY_TRAIN_AND_STATION_ID = "SELECT * FROM train_stations " +
            "WHERE station_id = ? AND train_id = ?";
    private String GET_ALL = "SELECT * FROM train_stations";
    private String DELETE_BY_TRAIN_AND_STATION_ID = "DELETE FROM train_stations WHERE station_id = ? AND train_id = ?";
    private String UPDATE_BY_TRAIN_AND_STATION_ID = "UPDATE train_stations SET name=? WHERE station_id = ? AND train_id = ?";
    private String GET_BY_TRAIN_ID_AND_ORDER = "SELECT * FROM train_stations WHERE train_id=? AND train_stations.order=?";
    private String GET_LAST_ROOT_STATION_BY_TRAIN_ID = "SELECT * FROM train_stations WHERE train_id=? ORDER BY train_stations.order DESC LIMIT 1";

    public TrainStationDaoImpl(Connection connection) {
        super(connection, new TrainStationMapper());
        LOG = Logger.getLogger(TrainStationDaoImpl.class);
    }

    @Override
    public int create(TrainStation trainStation) {
        return create(INSERT, statement -> {
            statement.setInt(1, trainStation.getStation().getId());
            statement.setInt(2, trainStation.getTrain().getId());
            statement.setInt(3, trainStation.getOrder());
            statement.setInt(4, trainStation.getPrise());
            statement.setTime(5, trainStation.getArrivalTime());
            statement.setTime(6, trainStation.getDepartureTime());
        });
    }

    @Override
    public Optional<TrainStation> getById(int id) {
        throw new java.lang.UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean delete(int id) {
        throw new java.lang.UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean update(TrainStation trainStation) {
        throw new java.lang.UnsupportedOperationException("Not supported.");
    }

    @Override
    public List<TrainStation> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }

    @Override
    public Optional<TrainStation> getByTrainIdAndStationId(int stationId, int trainId) {
        return getById(GET_BY_TRAIN_AND_STATION_ID, statement -> {
            statement.setInt(1, stationId);
            statement.setInt(2, trainId);
        });
    }

    @Override
    public Optional<TrainStation> getByTrainIdAndOrder(int trainId, int order){
        return getById(GET_BY_TRAIN_ID_AND_ORDER, statement -> {
            statement.setInt(1, trainId);
            statement.setInt(2, order);
        });
    }

    @Override
    public Optional<TrainStation> getLastRootByTrainId(int trainId){
        return getById(GET_LAST_ROOT_STATION_BY_TRAIN_ID, statement -> {
            statement.setInt(1, trainId);
        });
    }

}