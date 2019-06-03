package ticketoffice.facade.train;

import org.apache.log4j.Logger;
import ticketoffice.dto.train.FullTrainInfoDto;
import ticketoffice.facade.coach.TrainCoachPlacesInfoFacade;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainDao;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;
import java.util.Optional;

public class FullTrainInfoFacade {

    private static Logger LOG = Logger.getLogger(FullTrainInfoFacade.class);

    private TrainStationService trainStationService = new TrainStationService();
    private TrainCoachPlacesInfoFacade trainCoachPlacesInfoFacade = new TrainCoachPlacesInfoFacade();

    public FullTrainInfoDto getRequestTrainInformation
            (int departureStationId, int destinationStationId, Date departureDate, int requestTrainId) {

        FullTrainInfoDto fullTrainInfoDto = new FullTrainInfoDto();
        Train requestTrain;
        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()) {
            Optional<Train> trainOptional = trainDao.getById(requestTrainId);
            if (trainOptional.isPresent()) {
                requestTrain = trainOptional.get();
            } else {
                String message = "Train " + requestTrainId + " doesn't exist";
                LOG.error(message);
                throw new IllegalArgumentException(message); //change exception
            }
        }
        fullTrainInfoDto.setTrain(requestTrain);

        fullTrainInfoDto.setDepartureTime(trainStationService.getDepartureTime(departureStationId, requestTrainId));
        fullTrainInfoDto.setArrivalTime(trainStationService.getArrivalTime(destinationStationId, requestTrainId));

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
