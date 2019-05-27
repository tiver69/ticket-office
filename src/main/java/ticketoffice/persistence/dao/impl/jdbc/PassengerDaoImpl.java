package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;
import ticketoffice.persistence.mapper.PassengerMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PassengerDaoImpl extends AbstractDaoImpl<Passenger> implements PassengerDao {

    private String INSERT = "INSERT INTO passengers (id, last_name, first_name, login, password)" +
            "VALUES (?,?,?,?,?)";
    private String GET_BY_ID = "SELECT * FROM passengers WHERE id = ?";
    private String GET_ALL = "SELECT * FROM passengers";
    private String DELETE = "DELETE FROM passengers WHERE id=?";
    private String UPDATE = "UPDATE passengers SET last_name=?, first_name=?, " +
            "login=?, password=? WHERE id=?";
    private String GET_BY_LOGIN = "SELECT * FROM passengers WHERE login = ?";


    public PassengerDaoImpl(Connection connection) {
        super(connection, new PassengerMapper());
        LOG = Logger.getLogger(PassengerDaoImpl.class);
    }

    @Override
    public int create(Passenger passenger) {
        return create(INSERT, statement -> {
            statement.setInt(1, passenger.getId());
            statement.setString(2, passenger.getLastName());
            statement.setString(3, passenger.getFirstName());
            statement.setString(4, passenger.getLogin());
            statement.setString(5, passenger.getPassword());
        });
    }

    @Override
    public Passenger getById(int id) {
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
    public boolean update(Passenger passenger) {
        return update(UPDATE, statement -> {
            statement.setString(1, passenger.getLastName());
            statement.setString(2, passenger.getFirstName());
            statement.setString(3, passenger.getLogin());
            statement.setString(4, passenger.getPassword());
            statement.setInt(5, passenger.getId());
        });
    }

    @Override
    public List<Passenger> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }

    @Override
    public Passenger getByLogin(String login) {
        return getById(GET_BY_LOGIN, statement -> {
            statement.setString(1, login);
        });
    }

    @Override
    public int createUser(Passenger passenger) {
        try {
            connection.setAutoCommit(false);
            int passengerCommit = this.create(passenger);
            Passenger newPassenger = getByLogin(passenger.getLogin());

            PassengerRole passengerRole = new PassengerRole();
            passengerRole.setPassenger(newPassenger);
            passengerRole.setRole(Role.USER);
            PassengerRoleDao passengerRoleDao = new PassengerRoleDaoImpl(connection);
            int passengerRoleCommit = passengerRoleDao.create(passengerRole);

            if (passengerRoleCommit + passengerCommit != 2) {
                connection.rollback();
                return 0;
            }
            connection.commit();
        } catch (SQLException e){
            LOG.error(e.getMessage());
        }
        return 1;
    }
}
