package ticketoffice.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ticketoffice.model.*;
import ticketoffice.model.builders.TicketBuilder;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.impl.jdbc.PassengerDaoImpl;
import ticketoffice.persistence.dao.impl.jdbc.StationDaoImpl;
import ticketoffice.persistence.dao.impl.jdbc.TicketDaoImpl;
import ticketoffice.persistence.dao.impl.jdbc.TrainCoachDaoImpl;

import java.sql.Date;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TicketServiceTest {

    private static Ticket ticket;
    private static Date TEST_DATE = Date.valueOf("2019-06-12");
    private static int DEP_STATION_ID = 2;
    private static int DEST_STATION_ID = 1;
    private static int COACH_ID = 19;
    private TicketService ticketService = new TicketService();

    @BeforeClass
    public static void setUpClass() throws SQLException {

        Station departureStation = new Station();
        departureStation.setId(DEP_STATION_ID);
        Station destinationStation = new Station();
        destinationStation.setId(DEST_STATION_ID);
        Train train = new TrainService().getTrain(732);
        CoachType coachType = new CoachTypeService().getCoachType(1,"ru");
        TrainCoach trainCoach = new TrainCoach();
        trainCoach.setId(COACH_ID);
        trainCoach.setTrain(train);
        trainCoach.setCoachType(coachType);

        TicketBuilder ticketBuilder = new TicketBuilder();
        ticketBuilder
                .setDepartureStation(departureStation)
                .setDestinationStation(destinationStation)
                .setTrainCoach(trainCoach)
                .setDate(TEST_DATE)
                .setPlace(17);
        ticket = ticketBuilder.build();
    }

    @Test
    public void countTicketPriceTest() {
        int result = ticketService.countTicketPrice(ticket);

        int rootPrice = 50*7;
        int expected = rootPrice +ticket.getTrainCoach().getTrain().getMarkup() * rootPrice / 100 +
                ticket.getTrainCoach().getCoachType().getMarkup() * rootPrice / 100;
        Assert.assertThat("Prices must be equal",
                result, equalTo(expected));
    }

    @Test
    public void fillTicket() {
        ticketService.fillTicket(ticket, "en");
        Assert.assertThat("Departure stations must be equal",
                ticket.getDepartureStation().getName(), equalTo("Kyiv-Pasazhyrsky"));
        Assert.assertThat("Destination stations must be equal",
                ticket.getDestinationStation().getName(), equalTo("Zaporizhzhya 1"));
    }

    @Test
    public void checkIfPlaceUnavailableForTicket() {
        boolean status = ticketService.checkIfPlaceUnavailableForTicket(ticket);
        Assert.assertFalse(status);
    }

    @Test
    public void checkIfTicketAvailableForRootTest() {
        boolean status = ticketService.checkIfTicketAvailableForRoot(3,5, ticket);
        Assert.assertFalse(status);
    }
}