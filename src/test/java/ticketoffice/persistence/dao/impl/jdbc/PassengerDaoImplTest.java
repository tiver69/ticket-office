package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.CoachType;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.persistence.dao.interfaces.StationDao;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PassengerDaoImplTest {

    private static PassengerDao passengerDao;
    private static Connection connection;
    private static Passenger passenger;

    private static int PASSENGER_ID = 1703;
    private static String PASSENGER_LOGIN = "testLogin";

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        passengerDao = new PassengerDaoImpl(connection);

        passenger = new Passenger();
        passenger.setId(PASSENGER_ID);
        passenger.setFirstName("testFirst");
        passenger.setLastName("testLast");
        passenger.setLogin(PASSENGER_LOGIN);
        passenger.setPassword("test");
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        passengerDao.close();
    }

    @After
    public void afterTest() {
        passengerDao.delete(PASSENGER_ID);
    }

    @Test
    public void createAndGetByIdTest() {
        passengerDao.create(passenger);
        Passenger getPassenger = passengerDao.getById(PASSENGER_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                passenger, equalTo(getPassenger));
    }

    @Test
    public void update() {
        passengerDao.create(passenger);
        passenger.setFirstName("newTestFirst");
        passengerDao.update(passenger);
        Passenger getPassenger = passengerDao.getById(PASSENGER_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                passenger, equalTo(getPassenger));
    }

    @Test
    public void getAllTest() {
        int sizeBefore = passengerDao.getAll().size();
        passengerDao.create(passenger);
        int sizeAfter = passengerDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }

    @Test
    public void getByLoginTest() {
        passengerDao.create(passenger);
        Passenger getPassenger = passengerDao.getByLogin(PASSENGER_LOGIN).get();
        Assert.assertThat("Item from db mush me equal to test one",
                passenger, equalTo(getPassenger));
    }
}
