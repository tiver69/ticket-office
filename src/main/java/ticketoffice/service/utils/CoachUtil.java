package ticketoffice.service.utils;

import ticketoffice.model.TrainCoach;

import java.util.List;

public class CoachUtil {

    public static int countTotalPlacesForCoachType(List<TrainCoach> trainCoachList){
        return trainCoachList.size()*trainCoachList.get(0).getCoachType().getPlaces();
    }
}
