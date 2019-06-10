package ticketoffice.persistence.dao.impl.jdbc;

import org.junit.*;
import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.builders.TicketBuilder;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TicketDaoImplTest {

    private static TicketDao ticketDao;
    private static Connection connection;
    private static Ticket ticket;

    private static Date TEST_DATE = Date.valueOf("2019-06-12");
    private static int PASSENGER_ID = 1;
    private static int COACH_ID = 19;
    private static int TICKET_ID = 1703;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
        ticketDao = new TicketDaoImpl(connection);

        TicketBuilder ticketBuilder = new TicketBuilder();
        ticketBuilder.setId(TICKET_ID)
                .setPassenger(new PassengerDaoImpl(connection).getById(PASSENGER_ID).get())
                .setDepartureStation(new StationDaoImpl(connection).getById(2).get())
                .setDestinationStation(new StationDaoImpl(connection).getById(1).get())
                .setTrainCoach(new TrainCoachDaoImpl(connection).getById(COACH_ID).get())
                .setDate(TEST_DATE)
                .setPlace(17)
                .setPrice(17);
        ticket = ticketBuilder.build();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.rollback();
        ticketDao.close();
    }

    @After
    public void afterTest() {
        ticketDao.delete(ticket.getId());
    }

    @Test
    public void createAndGetByIdTest() {
        ticketDao.create(ticket);
        Optional<Ticket> getTicket = ticketDao.getById(TICKET_ID);
        Assert.assertTrue("Item from db must be present",
                getTicket.isPresent());
    }

    @Test
    public void update() {
        ticketDao.create(ticket);
        ticket.setPlace(18);
        ticketDao.update(ticket);
        Ticket getTicket = ticketDao.getById(TICKET_ID).get();
        Assert.assertThat("Item from db mush me equal to test one",
                ticket.getPlace(), equalTo(getTicket.getPlace()));
    }

    @Test
    public void getAll() {
        int sizeBefore = ticketDao.getAll().size();
        ticketDao.create(ticket);
        int sizeAfter = ticketDao.getAll().size();
        Assert.assertThat("Size of data in db before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }

    @Test
    public void getTicketsByCoachIdAndDate() {
        int sizeBefore = ticketDao.getTicketsByCoachIdAndDate
                (COACH_ID, TEST_DATE).size();
        ticketDao.create(ticket);
        int sizeAfter = ticketDao.getTicketsByCoachIdAndDate
                (COACH_ID, TEST_DATE).size();
        Assert.assertThat("Size of data in db for 19 coach before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }

    @Test
    public void getTicketsByPassengerId() {
        int sizeBefore = ticketDao.getTicketsByPassengerId(PASSENGER_ID).size();
        ticketDao.create(ticket);
        int sizeAfter = ticketDao.getTicketsByPassengerId(PASSENGER_ID).size();
        Assert.assertThat("Size of data in db for 1st passenger before insert must be one lesser",
                sizeBefore, equalTo(sizeAfter - 1));
    }
}
