package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.persistence.mapper.CoachTypeMapper;
import ticketoffice.persistence.mapper.TrainCoachMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CoachTypeDaoImpl extends AbstractDaoImpl<CoachType> implements CoachTypeDao {

    private String INSERT = "INSERT INTO coach_types (id, name, places, markup) VALUES (?,?,?,?)";
    private String GET_BY_ID = "SELECT * FROM coach_types WHERE id = ?";
    private String GET_ALL = "SELECT * FROM coach_types";
    private String DELETE = "DELETE FROM coach_types WHERE id=?";
    private String UPDATE = "UPDATE coach_types SET name=?, places=?, " +
            "markup=? WHERE id=?";

    public CoachTypeDaoImpl(Connection connection) {
        super(connection, new CoachTypeMapper());
        LOG = Logger.getLogger(CoachTypeDaoImpl.class);
    }

    @Override
    public int create(CoachType coachType) {
        return create(INSERT, statement -> {
            statement.setInt(1, coachType.getId());
            statement.setString(2, coachType.getName());
            statement.setInt(3, coachType.getPlaces());
            statement.setInt(4, coachType.getMarkup());
        });
    }

    @Override
    public Optional<CoachType> getById(int id) {
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
    public boolean update(CoachType coachType) {
        return update(UPDATE, statement -> {
            statement.setString(1, coachType.getName());
            statement.setInt(2, coachType.getPlaces());
            statement.setInt(3, coachType.getMarkup());
            statement.setInt(4, coachType.getId());
        });
    }

    @Override
    public List<CoachType> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }
}

