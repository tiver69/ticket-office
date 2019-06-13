package ticketoffice.service;

import org.junit.Test;
import ticketoffice.exceptions.ValidateFailException;

public class CoachTypeServiceTest {

    private static CoachTypeService coachTypeService = new CoachTypeService();
    private static int COACH_TYPE_ID = 1703;

    @Test(expected = ValidateFailException.class)
    public void getCoachType() {
         coachTypeService.getCoachType(COACH_TYPE_ID, "ru");
    }
}