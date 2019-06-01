package ticketoffice.service.utils;

import ticketoffice.dto.coach.TrainCoachPlacesInfoDto;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;

import java.util.*;

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

    public static void sortByCoachNumber(List<TrainCoachPlacesInfoDto> trainCoachPlacesInfoDtoList){
        trainCoachPlacesInfoDtoList.sort(
                Comparator.comparingInt(o -> o.getTrainCoach().getNumber()));
    }

}
