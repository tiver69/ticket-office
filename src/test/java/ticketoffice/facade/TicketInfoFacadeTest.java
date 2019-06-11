package ticketoffice.facade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.model.*;
import ticketoffice.model.builders.TicketBuilder;
import ticketoffice.service.CoachTypeService;
import ticketoffice.service.TrainService;
import ticketoffice.service.TrainStationService;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketInfoFacadeTest {

    private static String DATE = "2019-06-12";
    private static String TOMORROW_DATE = "2019-06-13";
    private static String DEP_TIME = "14:02:02";
    private static String ARR_TIME = "03:03:03";

    @InjectMocks
    private TicketInfoFacade ticketInfoFacade = new TicketInfoFacade();
    private static Ticket ticket;

    @Mock
    TrainStationService trainStationService;
    @Mock
    TrainStation trainStation;

    @BeforeClass
    public static void setUpClass() {
        Date TEST_DATE = Date.valueOf(DATE);
        Station departureStation = new Station();
        departureStation.setId(2);
        Station destinationStation = new Station();
        destinationStation.setId(1);
        Train train = new TrainService().getTrain(732);
        CoachType coachType = new CoachTypeService().getCoachType(1, "ru");
        TrainCoach trainCoach = new TrainCoach();
        trainCoach.setId(19);
        trainCoach.setTrain(train);
        trainCoach.setCoachType(coachType);

        TicketBuilder ticketBuilder = new TicketBuilder();
        ticketBuilder
                .setDepartureStation(departureStation)
                .setDestinationStation(destinationStation)
                .setTrainCoach(trainCoach)
                .setDate(TEST_DATE);
        ticket = ticketBuilder.build();
    }

    @Before
    public void setUp() {
        when(trainStationService.getTrainStation(anyInt(), anyInt()))
                .thenReturn(trainStation);
        when(trainStation.getDepartureTime()).thenReturn(Time.valueOf(DEP_TIME));
        when(trainStation.getArrivalTime()).thenReturn(Time.valueOf(ARR_TIME));

    }

    @Test
    public void loadTicketInfoTest() {
        TicketInfoDto loadTicketInfo = ticketInfoFacade.loadTicketInfo(ticket);
        Assert.assertThat(loadTicketInfo.getDepartureDateTime(),
                equalTo(Timestamp.valueOf(DATE + " " + DEP_TIME)));
        Assert.assertThat(loadTicketInfo.getArrivalDateTime(),
                equalTo(Timestamp.valueOf(TOMORROW_DATE + " " + ARR_TIME)));
    }
}
