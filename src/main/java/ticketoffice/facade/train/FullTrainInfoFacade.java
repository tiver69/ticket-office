package ticketoffice.facade.train;

import org.apache.log4j.Logger;
import ticketoffice.dto.train.FullTrainInfoDto;
import ticketoffice.facade.coach.TrainCoachPlacesInfoFacade;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;
import ticketoffice.service.TrainService;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;

public class FullTrainInfoFacade {

    private static Logger LOG = Logger.getLogger(FullTrainInfoFacade.class);

    private TrainService trainService = new TrainService();
    private TrainStationService trainStationService = new TrainStationService();
    private TrainCoachPlacesInfoFacade trainCoachPlacesInfoFacade = new TrainCoachPlacesInfoFacade();

    public FullTrainInfoDto getRequestTrainInformation
            (int departureStationId, int destinationStationId, Date departureDate, int requestTrainId) {

        FullTrainInfoDto fullTrainInfoDto = new FullTrainInfoDto();
        fullTrainInfoDto.setTrain(
                trainService.getTrain(requestTrainId));

        fullTrainInfoDto.setDepartureTime(trainStationService.
                getTrainStation(departureStationId, requestTrainId).getDepartureTime());
        fullTrainInfoDto.setArrivalTime(trainStationService.
                getTrainStation(destinationStationId, requestTrainId).getArrivalTime());

        try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
            trainStationDao.getByTrainIdAndOrder(requestTrainId, 0)
                    .ifPresent(trainStation -> {
                        trainStationService.fillTrainStation(trainStation);
                        fullTrainInfoDto.setFirstRootStation(trainStation.getStation());
                    });
            trainStationDao.getLastRootByTrainId(requestTrainId)
                    .ifPresent(trainStation -> {
                        trainStationService.fillTrainStation(trainStation);
                        fullTrainInfoDto.setLastRootStation(trainStation.getStation());
                    });
        }

        fullTrainInfoDto.setDuration(
                TimeDateUtil.getTimeDiff(fullTrainInfoDto.getDepartureTime(), fullTrainInfoDto.getArrivalTime()));

        fullTrainInfoDto.setTrainCoachPlacesInfoDtoList(
                trainCoachPlacesInfoFacade.getTrainCoachPlacesInformation(requestTrainId, departureStationId,
                        destinationStationId, departureDate));

        return fullTrainInfoDto;
    }
}
