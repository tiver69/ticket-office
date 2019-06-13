package ticketoffice.service.utils;

import ticketoffice.model.TrainCoach;

import java.util.List;

public class CoachUtil {

    /**
     * Count summ of total places in all train coaches from passed list.
     *
     * @param trainCoachList
     * @return -   int variable of counted summ
     */
    public static int countTotalPlacesForCoachType(List<TrainCoach> trainCoachList) {
        return trainCoachList.size() * trainCoachList.get(0).getCoachType().getPlaces();
    }
}
