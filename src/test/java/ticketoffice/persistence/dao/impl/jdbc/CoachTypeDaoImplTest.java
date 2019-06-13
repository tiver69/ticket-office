package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.CoachType;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;

public class CoachTypeDaoImplTest {

    private static CoachTypeDao coachTypeDao;
    private static Connection connection;
    private static CoachType coachType;

    private static int COACH_TYPE_ID = 1703;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        coachTypeDao = new CoachTypeDaoImpl(connection);

        coachType = new CoachType();
        coachType.setId(COACH_TYPE_ID);
        coachType.setName("test");
        coachType.setMarkup(17);
        coachType.setPlaces(17);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        coachTypeDao.close();
    }

    @After
    public void afterTest() {
        coachTypeDao.delete(COACH_TYPE_ID);
    }

    @Test
    public void createAndGetTest() {
        coachTypeDao.create(coachType);
        CoachType getCoachType = coachTypeDao.getById(COACH_TYPE_ID).get();
        Assert.assertThat("Item from db mush me equal to test one", coachType, equalTo(getCoachType));
    }

    @Test
    public void updateTest() {
        coachTypeDao.create(coachType);
        coachType.setName("test2");
        coachTypeDao.update(coachType);
        CoachType getCoachType = coachTypeDao.getById(COACH_TYPE_ID).get();
        Assert.assertThat("Item from db mush me equal to test one", coachType, equalTo(getCoachType));
    }

    @Test
    public void getAllTest() {
        int sizeBefore = coachTypeDao.getAll().size();
        coachTypeDao.create(coachType);
        int sizeAfter = coachTypeDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }
}
