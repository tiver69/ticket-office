package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.Train;
import ticketoffice.model.TrainCoach;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TrainCoachDaoImplTest {

    private static TrainCoachDao trainCoachDao;
    private static Connection connection;
    private static TrainCoach trainCoach;

    private static int TRAIN_COACH_ID = 1703;
    private static int TRAIN_ID = 72;
    private static int COACH_TYPE_ID = 3;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        trainCoachDao = new TrainCoachDaoImpl(connection);

        trainCoach = new TrainCoach();
        trainCoach.setId(TRAIN_COACH_ID);
        trainCoach.setTrain(new TrainDaoImpl(connection).getById(TRAIN_ID).get());
        trainCoach.setCoachType(new CoachTypeDaoImpl(connection).getById(COACH_TYPE_ID).get());
        trainCoach.setNumber(17);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        trainCoachDao.close();
    }

    @After
    public void afterTest() {
        trainCoachDao.delete(TRAIN_COACH_ID);
    }

    @Test
    public void createAndGetByIdTest() {
        trainCoachDao.create(trainCoach);
        Optional<TrainCoach> getTrainCoach = trainCoachDao.getById(TRAIN_COACH_ID);
        Assert.assertTrue("Item from db must be present",
                getTrainCoach.isPresent());
    }

    @Test
    public void updateTest() {
        trainCoachDao.create(trainCoach);
        trainCoach.setNumber(18);
        trainCoachDao.update(trainCoach);
        TrainCoach getTrainCoach = trainCoachDao.getById(TRAIN_COACH_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                trainCoach.getNumber(), equalTo(getTrainCoach.getNumber()));
    }

    @Test
    public void getAllTest() {
        int sizeBefore = trainCoachDao.getAll().size();
        trainCoachDao.create(trainCoach);
        int sizeAfter = trainCoachDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }

    @Test
    public void getByTrainIdTest() {
        int sizeBefore = trainCoachDao.getByTrainId(TRAIN_ID).size();
        trainCoachDao.create(trainCoach);
        int sizeAfter = trainCoachDao.getByTrainId(TRAIN_ID).size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }
}
