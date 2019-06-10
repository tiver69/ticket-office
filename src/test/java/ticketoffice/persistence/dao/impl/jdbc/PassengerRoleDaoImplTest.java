package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PassengerRoleDaoImplTest {

    private static PassengerRoleDao passengerRoleDao;
    private static Connection connection;
    private static PassengerRole passengerRole;

    private static int PASSENGER_ROLE_ID = 1703;
    private static int PASSENGER_ID = 1;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        passengerRoleDao = new PassengerRoleDaoImpl(connection);

        passengerRole = new PassengerRole();
        passengerRole.setId(PASSENGER_ROLE_ID);
        passengerRole.setPassenger(new PassengerDaoImpl(connection).getById(PASSENGER_ID).get());
        passengerRole.setRole(Role.USER);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        passengerRoleDao.close();
    }

    @After
    public void afterTest() {
        passengerRoleDao.delete(PASSENGER_ROLE_ID);
    }

    @Test
    public void createAndGetByIdTest() {
        passengerRoleDao.create(passengerRole);
        PassengerRole getPassengerRole = passengerRoleDao.getById(PASSENGER_ROLE_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                passengerRole, equalTo(getPassengerRole));
    }

    @Test
    public void updateTest() {
        passengerRoleDao.create(passengerRole);
        passengerRole.setRole(Role.USER);
        passengerRoleDao.update(passengerRole);
        PassengerRole getPassengerRole = passengerRoleDao.getById(PASSENGER_ROLE_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                passengerRole, equalTo(getPassengerRole));
    }

    @Test
    public void getAllTest() {
        int sizeBefore = passengerRoleDao.getAll().size();
        passengerRoleDao.create(passengerRole);
        int sizeAfter = passengerRoleDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }

    @Test
    public void getPassengerRolesByPassengerIdTest() {
        int countRolesBefore = passengerRoleDao.getPassengerRolesByPassengerId(
                PASSENGER_ID).size();
        passengerRoleDao.create(passengerRole);
        int countRolesAfter = passengerRoleDao.getPassengerRolesByPassengerId(
                PASSENGER_ID).size();
        Assert.assertThat("TSize of data in db before insert must be one lesser",
                countRolesBefore, equalTo(countRolesAfter - 1));
    }
}
