package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.PassengerRole;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;
import ticketoffice.persistence.mapper.PassengerRoleMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class PassengerRoleDaoImpl extends AbstractDaoImpl<PassengerRole> implements PassengerRoleDao {

    private String INSERT = "INSERT INTO passenger_roles (id, passenger_id, role) VALUES (?,?,?)";
    private String GET_BY_ID = "SELECT * FROM passenger_roles WHERE id = ?";
    private String GET_ALL = "SELECT * FROM passenger_roles";
    private String DELETE = "DELETE FROM passenger_roles WHERE id=?";
    private String UPDATE = "UPDATE passenger_roles SET passenger_id=?, role=? WHERE id=?";

    private String GET_ALL_BY_PASSENGER_ID = "SELECT * FROM passenger_roles WHERE passenger_id=?";



    public PassengerRoleDaoImpl(Connection connection) {
        super(connection, new PassengerRoleMapper());
        LOG = Logger.getLogger(PassengerRoleDaoImpl.class);
    }

    @Override
    public int create(PassengerRole passengerRole) {
        return create(INSERT, statement -> {
            statement.setInt(1, passengerRole.getId());
            statement.setInt(2, passengerRole.getPassenger().getId());
            statement.setString(3, passengerRole.getRole().toString());
        });
    }

    @Override
    public Optional<PassengerRole> getById(int id) {
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
    public boolean update(PassengerRole passengerRole) {
        return update(UPDATE, statement -> {
            statement.setInt(1, passengerRole.getPassenger().getId());
            statement.setString(2, passengerRole.getRole().toString());
            statement.setInt(3, passengerRole.getId());
        });
    }

    @Override
    public List<PassengerRole> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }

    @Override
    public List<PassengerRole> getPassengerRolesByPassengerId(int passengerId) {
        return getAll(GET_ALL_BY_PASSENGER_ID, statement -> {
            statement.setInt(1, passengerId);
        });
    }
}
