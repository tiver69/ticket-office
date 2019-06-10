package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.persistence.dao.interfaces.StationDao;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class StationDaoImplTest {

    private static StationDao stationDao;
    private static Connection connection;
    private static Station station;

    private static int STATION_ID = 1703;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        stationDao = new StationDaoImpl(connection);

        station = new Station();
        station.setId(STATION_ID);
        station.setName("test");
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        stationDao.close();
    }

    @After
    public void afterTest() {
        stationDao.delete(STATION_ID);
    }

    @Test
    public void createAndGetByIdTest() {
        stationDao.create(station);
        Station getStation = stationDao.getById(STATION_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                station, equalTo(getStation));
    }

    @Test
    public void updateTest() {
        stationDao.create(station);
        station.setName("newTestName");
        stationDao.update(station);
        Station getStation = stationDao.getById(STATION_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                station, equalTo(getStation));
    }

    @Test
    public void getAllTest() {
        int sizeBefore = stationDao.getAll().size();
        stationDao.create(station);
        int sizeAfter = stationDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }
}
