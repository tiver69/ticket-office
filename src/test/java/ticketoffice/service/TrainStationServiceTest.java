package ticketoffice.service;

import org.junit.Assert;
import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.TrainStation;

import static org.hamcrest.CoreMatchers.equalTo;

public class TrainStationServiceTest {

    private TrainStationService trainStationService = new TrainStationService();
    private int TRAIN_ID = 732;
    private int STATION_ID = 1;

    @Test(expected = ValidateFailException.class)
    public void getTrainStation() {
        trainStationService.getTrainStation(1703, 1703);
    }

    @Test
    public void fillTrainStation() {
        TrainStation trainStation = trainStationService.getTrainStation(STATION_ID, TRAIN_ID);
        trainStationService.fillTrainStation(trainStation, "en");
        Assert.assertThat("Station names must be equal",
                trainStation.getStation().getName(), equalTo("Zaporizhzhya 1"));
        Assert.assertThat("Frequency must be equal to '2'",
                trainStation.getTrain().getFrequency(), equalTo(2));
    }

    @Test
    public void getTrainStationRoot() {
        int trainFound = trainStationService.
                getTrainStationRoot(2, STATION_ID, TRAIN_ID).size();
        Assert.assertThat("There must be 7 train-stations found",
                trainFound, equalTo(7));
    }

    @Test
    public void getTrainStationOrder() {
    }
}