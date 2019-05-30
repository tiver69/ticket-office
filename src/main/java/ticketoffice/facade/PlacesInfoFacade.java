package ticketoffice.facade;

import ticketoffice.dto.CoachPlacesInfoDto;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.service.TrainCoachService;
import ticketoffice.service.utils.CoachUtil;
import ticketoffice.service.utils.TrainCoachUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlacesInfoFacade {

    private TrainCoachService trainCoachService = new TrainCoachService();

    private CoachPlacesInfoDto getCoachPlacesInformation(List<TrainCoach> trainCoachList){
        CoachPlacesInfoDto coachPlacesInfoDto = new CoachPlacesInfoDto();
        coachPlacesInfoDto.setCoachType(trainCoachList.get(0).getCoachType());
        coachPlacesInfoDto.setQuantity(trainCoachList.size());
        coachPlacesInfoDto.setTotalPlaces(
                CoachUtil.countTotalPlacesForCoachType(trainCoachList));
        coachPlacesInfoDto.setAvailablePlaces(0);
        return coachPlacesInfoDto;
    }


    public List<CoachPlacesInfoDto> getTrainPlacesInformation(int trainId){

        List<CoachPlacesInfoDto> trainPlacesInfoList = new ArrayList<>();

        Map<CoachType, List<TrainCoach>> coachTypeMap;
        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()){
            List<TrainCoach> trainCoachList = trainCoachDao.getByTrainId(trainId);
            trainCoachList.forEach(trainCoach -> {
                trainCoachService.fillTrainCoach(trainCoach);
            });
            coachTypeMap = TrainCoachUtil.groupByCoachType(trainCoachList);
        }

        coachTypeMap.keySet().forEach(key->{            ;
            trainPlacesInfoList.add(
                    getCoachPlacesInformation(coachTypeMap.get(key)));
        });

        return trainPlacesInfoList;
    }
}
