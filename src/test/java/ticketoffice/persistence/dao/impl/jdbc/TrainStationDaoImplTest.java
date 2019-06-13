package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;

public class TrainStationDaoImplTest {

    private static TrainStationDao trainStationDao;
    private static Connection connection;

    private static int TRAIN_ID = 72;
    private static int STATION_ID = 1;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        trainStationDao = new TrainStationDaoImpl(connection);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        trainStationDao.close();
    }

    @After
    public void afterTest() {

    }

    @Test
    public void getByTrainIdAndStationIdTest() {
        Optional<TrainStation> trainStation =
                trainStationDao.getByTrainIdAndStationId(STATION_ID, TRAIN_ID);
        Assert.assertTrue("Item from db must be present",
                trainStation.isPresent());
    }

    @Test
    public void getByTrainIdAndOrderTest() {
        TrainStation lastTrainRoot =
                trainStationDao.getByTrainIdAndOrder(TRAIN_ID, 0).get();
        Assert.assertThat("#0 order station for " + TRAIN_ID + "must be station#2" ,
                lastTrainRoot.getStation().getId(), equalTo(2));
    }

    @Test
    public void getLastRootByTrainIdTest() {
        TrainStation lastTrainRoot =
                trainStationDao.getLastRootByTrainId(TRAIN_ID).get();
        Assert.assertThat("Last root for " + TRAIN_ID + "must be station#1" ,
                lastTrainRoot.getStation().getId(), equalTo(1));
    }

    @Test
    public void getFullRootByTrainIdTest() {
        int trainRoot =
                trainStationDao.getFullRootByTrainId(TRAIN_ID).size();
        Assert.assertThat("Root for " + TRAIN_ID + "must be 5 stations" ,
                trainRoot, equalTo(5));
    }
}
