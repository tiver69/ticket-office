package ticketoffice.service;

import org.junit.Assert;
import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;

import java.sql.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TrainServiceTest {

    private TrainService trainService = new TrainService();
    private int TRAIN_ID = 1703;
    private Date date = Date.valueOf("2019-06-11");

    @Test(expected = ValidateFailException .class)
    public void getTrain() {
        trainService.getTrain(TRAIN_ID);
    }

    @Test
    public void findTrainInDirection() {
        int trainFound = trainService.findTrainInDirection(2,1,date).size();
        Assert.assertThat("There must be 2 results found",
                trainFound, equalTo(2));
    }
}