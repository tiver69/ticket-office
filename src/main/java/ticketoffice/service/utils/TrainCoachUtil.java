package ticketoffice.service.utils;

import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainCoachUtil {

    public static Map<CoachType, List<TrainCoach>> groupByCoachType(List<TrainCoach> trainCoachList){
        Map<CoachType, List<TrainCoach>> grouped = new HashMap<>();

        trainCoachList.forEach(trainCoach -> {
            if (!grouped.containsKey(trainCoach.getCoachType())){
                grouped.put(trainCoach.getCoachType(), new ArrayList<>());
            }
            grouped.get(trainCoach.getCoachType()).add(trainCoach);
        });
        return grouped;
    }

}
