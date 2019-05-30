package ticketoffice.facade;

import ticketoffice.dto.CoachPlacesInfoDto;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.service.TrainCoachService;
import ticketoffice.service.utils.CoachUtil;
import ticketoffice.service.utils.TrainCoachUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlacesInfoFacade {

    private TrainCoachService trainCoachService = new TrainCoachService();

    private CoachPlacesInfoDto getCoachPlacesInformation(List<TrainCoach> trainCoachList, Date departureDate) {
        CoachPlacesInfoDto coachPlacesInfoDto = new CoachPlacesInfoDto();
        coachPlacesInfoDto.setCoachType(trainCoachList.get(0).getCoachType());
        coachPlacesInfoDto.setQuantity(trainCoachList.size());
        coachPlacesInfoDto.setTotalPlaces(
                CoachUtil.countTotalPlacesForCoachType(trainCoachList));
        try(TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()){
            int occupiedPlace =
            trainCoachList.stream()
                    .mapToInt(trainCoach -> ticketDao.getTicketsByCoachIdAndDate(trainCoach.getId(), departureDate).size())
                    .sum();
            coachPlacesInfoDto.setAvailablePlaces(coachPlacesInfoDto.getTotalPlaces() - occupiedPlace);
        }
        return coachPlacesInfoDto;
    }


    public List<CoachPlacesInfoDto> getTrainPlacesInformation(int trainId, Date departureDate) {

        Map<CoachType, List<TrainCoach>> coachTypeMap;
        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()) {
            List<TrainCoach> trainCoachList = trainCoachDao.getByTrainId(trainId);
            trainCoachList.forEach(trainCoach -> {
                trainCoachService.fillTrainCoach(trainCoach);
            });
            coachTypeMap = TrainCoachUtil.groupByCoachType(trainCoachList);
        }

        List<CoachPlacesInfoDto> trainPlacesInfoList = new ArrayList<>();
        coachTypeMap.keySet().forEach(key -> {
            ;
            trainPlacesInfoList.add(
                    getCoachPlacesInformation(coachTypeMap.get(key), departureDate));
        });

        return trainPlacesInfoList;
    }
}
