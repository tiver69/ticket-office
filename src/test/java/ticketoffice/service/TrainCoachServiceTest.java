package ticketoffice.service;

import org.junit.Assert;
import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TrainCoachServiceTest {

    private static TrainCoachService trainCoachService = new TrainCoachService();
    private static int TRAIN_COACH_ID = 1703;

    @Test(expected = ValidateFailException.class)
    public void getTrainCoach() {
        trainCoachService.getTrainCoach(TRAIN_COACH_ID, "ru");
    }

    @Test
    public void fillTrainCoachType() {
        CoachType coachType = new CoachType();
        coachType.setId(1);
        TrainCoach trainCoach = new TrainCoach();
        trainCoach.setId(19);
        trainCoach.setCoachType(coachType);
        trainCoachService.fillTrainCoachType(trainCoach, "en");
        Assert.assertThat("Prices must be equal",
                "Seating first class", equalTo(trainCoach.getCoachType().getName()));
    }
}