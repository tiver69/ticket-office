package ticketoffice.service.utils;

import ticketoffice.dto.coach.TrainCoachPlacesInfoDto;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;

import java.util.*;

public class TrainCoachUtil {

    /**
     * @param trainCoachList
     * @return Map of Train Coach list, grouped by Coach Type.
     */
    public static Map<CoachType, List<TrainCoach>> groupByCoachType(List<TrainCoach> trainCoachList) {
        Map<CoachType, List<TrainCoach>> grouped = new HashMap<>();

        trainCoachList.forEach(trainCoach -> {
            if (!grouped.containsKey(trainCoach.getCoachType())) {
                grouped.put(trainCoach.getCoachType(), new ArrayList<>());
            }
            grouped.get(trainCoach.getCoachType()).add(trainCoach);
        });
        return grouped;
    }

    /**
     * Sort given List increasing number of Coach in Train.
     *
     * @param trainCoachPlacesInfoDtoList
     */
    public static void sortByCoachNumber(List<TrainCoachPlacesInfoDto> trainCoachPlacesInfoDtoList) {
        trainCoachPlacesInfoDtoList.sort(
                Comparator.comparingInt(o -> o.getTrainCoach().getNumber()));
    }
}
