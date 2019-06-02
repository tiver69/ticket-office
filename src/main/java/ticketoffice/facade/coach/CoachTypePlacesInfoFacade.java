package ticketoffice.facade.coach;

import org.apache.log4j.Logger;
import ticketoffice.dto.coach.CoachTypePlacesInfoDto;
import ticketoffice.model.CoachType;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.service.TicketService;
import ticketoffice.service.TrainCoachService;
import ticketoffice.service.utils.CoachUtil;
import ticketoffice.service.utils.TrainCoachUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoachTypePlacesInfoFacade {

    private static Logger LOG = Logger.getLogger(CoachTypePlacesInfoFacade.class);
    private TrainCoachService trainCoachService = new TrainCoachService();
    private TicketService ticketService = new TicketService();

    private CoachTypePlacesInfoDto getCoachTypePlacesInformation(List<TrainCoach> trainCoachList, int departureStationId,
                                                                 int destinationStationId, Date departureDate) {
        CoachTypePlacesInfoDto coachTypePlacesInfoDto = new CoachTypePlacesInfoDto();
        coachTypePlacesInfoDto.setCoachType(trainCoachList.get(0).getCoachType());
        coachTypePlacesInfoDto.setQuantity(trainCoachList.size());
        coachTypePlacesInfoDto.setTotalPlaces(
                CoachUtil.countTotalPlacesForCoachType(trainCoachList));

        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            int occupiedPlace =
                    trainCoachList.stream()
                            .mapToInt(trainCoach ->
                                    ticketDao.getTicketsByCoachIdAndDate(trainCoach.getId(), departureDate)
                                            .stream().filter(ticket ->
                                            !ticketService.checkIfTicketAvailableForRoot(departureStationId, destinationStationId, ticket)
                                    ).toArray().length)
                            .sum();
            coachTypePlacesInfoDto.setAvailablePlaces(coachTypePlacesInfoDto.getTotalPlaces() - occupiedPlace);
            LOG.debug(String.format("Found %d tickets in %s coachType for %s",
                    occupiedPlace, trainCoachList.get(0).getCoachType(),
                    departureDate.toString()));
        }

        return coachTypePlacesInfoDto;
    }


    public List<CoachTypePlacesInfoDto> getTrainPlacesInformation(int trainId, int departureStationId,
                                                                  int destinationStationId, Date departureDate) {

        Map<CoachType, List<TrainCoach>> coachTypeMap;
        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()) {
            List<TrainCoach> trainCoachList = trainCoachDao.getByTrainId(trainId);
            trainCoachList.forEach(trainCoach -> {
                trainCoachService.fillTrainCoachType(trainCoach);
            });
            coachTypeMap = TrainCoachUtil.groupByCoachType(trainCoachList);
        }

        List<CoachTypePlacesInfoDto> trainPlacesInfoList = new ArrayList<>();
        coachTypeMap.keySet().forEach(key -> {
            trainPlacesInfoList.add(
                    getCoachTypePlacesInformation(coachTypeMap.get(key), departureStationId, destinationStationId, departureDate));
        });

        LOG.info(String.format("Prepare information about %d coach types in train#%d",
                trainPlacesInfoList.size(), trainId));
        return trainPlacesInfoList;
    }
}
