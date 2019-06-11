package ticketoffice.service.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import ticketoffice.model.CoachType;
import ticketoffice.model.Train;
import ticketoffice.model.TrainCoach;
import ticketoffice.service.CoachTypeService;
import ticketoffice.service.TrainCoachService;
import ticketoffice.service.TrainService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachUtilTest {

    private static List<TrainCoach> trainCoachList = new ArrayList<>();
    private static int COACH_ID = 19;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        Train train = new TrainService().getTrain(732);
        CoachType coachType = new CoachTypeService().getCoachType(1,"ru");
        TrainCoach trainCoach = new TrainCoach();
        trainCoach.setId(COACH_ID);
        trainCoach.setTrain(train);
        trainCoach.setCoachType(coachType);
        new TrainCoachService().fillTrainCoachType(trainCoach, "en");

        for (int i=0; i<5; i++) trainCoachList.add(trainCoach);
    }

    @Test
    public void countTotalPlacesForCoachTypeTest(){
        int result = CoachUtil.countTotalPlacesForCoachType(trainCoachList);
    }

}