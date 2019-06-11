package ticketoffice.service;

import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;

import static org.junit.Assert.*;

public class StationServiceTest {

    private static StationService stationsService = new StationService();
    private static int STATION_ID = 1703;

    @Test(expected = ValidateFailException.class)
    public void getStation() {
        stationsService.getStation(STATION_ID, "ru");
    }
}
