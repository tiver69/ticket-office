package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.persistence.mapper.TrainCoachMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TrainCoachDaoImpl extends AbstractDaoImpl<TrainCoach> implements TrainCoachDao {

    private String INSERT = "INSERT INTO train_coaches (id, train_id, coach_type_id, number) VALUES (?,?,?,?)";
    private String GET_BY_ID = "SELECT * FROM train_coaches WHERE id = ?";
    private String GET_ALL = "SELECT * FROM train_coaches";
    private String DELETE = "DELETE FROM train_coaches WHERE id=?";
    private String UPDATE = "UPDATE train_coaches SET train_id=?, coach_type_id=?, " +
            "number=? WHERE id=?";
    private String GET_BY_TRAIN_ID = "SELECT * FROM train_coaches WHERE train_id=?";

    public TrainCoachDaoImpl(Connection connection) {
        super(connection, new TrainCoachMapper());
        LOG = Logger.getLogger(TrainCoachDaoImpl.class);
    }

    @Override
    public int create(TrainCoach trainCoach) {
        return create(INSERT, statement -> {
            statement.setInt(1, trainCoach.getId());
            statement.setInt(2, trainCoach.getTrain().getId());
            statement.setInt(3, trainCoach.getCoachType().getId());
            statement.setInt(4, trainCoach.getNumber());
        });
    }

    @Override
    public Optional<TrainCoach> getById(int id) {
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
    public boolean update(TrainCoach trainCoach) {
        return update(UPDATE, statement -> {
            statement.setInt(1, trainCoach.getTrain().getId());
            statement.setInt(2, trainCoach.getCoachType().getId());
            statement.setInt(3, trainCoach.getNumber());
            statement.setInt(4, trainCoach.getId());
        });
    }

    @Override
    public List<TrainCoach> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }


    @Override
    public List<TrainCoach> getByTrainId(int trainId){
        return getAll(GET_BY_TRAIN_ID, statement -> {
            statement.setInt(1, trainId);
        });
    }
}
