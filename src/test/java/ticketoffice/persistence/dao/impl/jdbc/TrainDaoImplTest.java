package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.Station;
import ticketoffice.model.Train;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TrainDaoImplTest {

    private static TrainDao trainDao;
    private static Connection connection;
    private static Train train;

    private static int TRAIN_ID = 1703;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        trainDao = new TrainDaoImpl(connection);

        train = new Train();
        train.setId(TRAIN_ID);
        train.setMarkup(17);
        train.setFrequency(17);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        trainDao.close();
    }

    @After
    public void afterTest() {
        trainDao.delete(TRAIN_ID);
    }

    @Test
    public void createAndGetByIdTest() {
        trainDao.create(train);
        Train getTrain = trainDao.getById(TRAIN_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                train, equalTo(getTrain));
    }

    @Test
    public void updateTest() {
        trainDao.create(train);
        train.setFrequency(18);
        trainDao.update(train);
        Train getTrain = trainDao.getById(TRAIN_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                train, equalTo(getTrain));
    }

    @Test
    public void getAllTest() {
        int sizeBefore = trainDao.getAll().size();
        trainDao.create(train);
        int sizeAfter = trainDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }

    @Test
    public void getTrainInDirectionTest() {
        int countTrains = trainDao.getTrainInDirection(2,1).size();
        Assert.assertThat("There must be 3 trains found",
                countTrains, equalTo(3));
    }
}
