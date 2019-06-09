package ticketoffice.facade.coach;

import org.apache.log4j.Logger;
import ticketoffice.dto.coach.TrainCoachPlacesInfoDto;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.dao.interfaces.TrainCoachDao;
import ticketoffice.service.TicketService;
import ticketoffice.service.TrainCoachService;
import ticketoffice.service.utils.TrainCoachUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TrainCoachPlacesInfoFacade {

    private static Logger LOG = Logger.getLogger(TrainCoachPlacesInfoFacade.class);
    private TrainCoachService trainCoachService = new TrainCoachService();
    private TicketService ticketService = new TicketService();


    private TrainCoachPlacesInfoDto
    getTrainCoachPlacesInformation(TrainCoach trainCoach, int departureStationId,
                                   int destinationStationId, Date departureDate) {
        TrainCoachPlacesInfoDto trainCoachPlacesInfoDto = new TrainCoachPlacesInfoDto();
        trainCoachPlacesInfoDto.setTrainCoach(trainCoach);
        List<Integer> placeList = new ArrayList<>();
        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            ticketDao.getTicketsByCoachIdAndDate(trainCoach.getId(), departureDate)
                    .stream().filter(ticket ->
                    !ticketService.checkIfTicketAvailableForRoot(departureStationId, destinationStationId, ticket))
                    .forEach(ticket -> {
                        placeList.add(ticket.getPlace());
                    });
        }
        trainCoachPlacesInfoDto.setBookedPlaceList(placeList);

        LOG.debug(String.format("Found %d tickets in %d coach for %s",
                placeList.size(), trainCoach.getNumber(), departureDate.toString()));
        return trainCoachPlacesInfoDto;
    }


    public List<TrainCoachPlacesInfoDto>
    getTrainCoachPlacesInformation(int trainId, int departureStationId, int destinationStationId,
                                   Date departureDate, String locale) {
        List<TrainCoach> trainCoachList;
        try (TrainCoachDao trainCoachDao = DaoFactory.getInstance().getTrainCoachDao()) {
            trainCoachList = trainCoachDao.getByTrainId(trainId);
            trainCoachList.forEach(trainCoach -> {
                trainCoachService.fillTrainCoachType(trainCoach, locale);
            });
        }

        List<TrainCoachPlacesInfoDto> trainCoachPlacesInfoDtoList = new ArrayList<>();
        trainCoachList.forEach(trainCoach -> {
            trainCoachPlacesInfoDtoList.add(
                    getTrainCoachPlacesInformation(trainCoach,
                            departureStationId, destinationStationId, departureDate));
        });
        TrainCoachUtil.sortByCoachNumber(trainCoachPlacesInfoDtoList);

        LOG.info(String.format("Prepare information about %d coaches in train#%d",
                trainCoachPlacesInfoDtoList.size(), trainId));

        return trainCoachPlacesInfoDtoList;
    }
}
