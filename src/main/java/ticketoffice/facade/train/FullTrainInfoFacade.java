package ticketoffice.facade.train;

import org.apache.log4j.Logger;
import ticketoffice.dto.train.FullTrainInfoDto;
import ticketoffice.facade.coach.TrainCoachPlacesInfoFacade;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;
import ticketoffice.service.TrainService;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;

/**
 * Service class for creating FullTrainInfoDto objects for each
 * coach of requested train
 */
public class FullTrainInfoFacade {

    private static Logger LOG = Logger.getLogger(FullTrainInfoFacade.class);

    private TrainService trainService = new TrainService();
    private TrainStationService trainStationService = new TrainStationService();
    private TrainCoachPlacesInfoFacade trainCoachPlacesInfoFacade = new TrainCoachPlacesInfoFacade();

    /**
     * Load information about each coach in requested train
     *
     * @param departureStationId   -   db id of requested departure station
     * @param destinationStationId -   db id of requested destination station
     * @param departureDate        -   request value of date
     * @param requestTrainId       -   requested train Id from db
     * @param locale               -   current session locale parameter
     * @return FullTrainInfoDto item
     */
    public FullTrainInfoDto
    getRequestTrainInformation(int departureStationId, int destinationStationId,
                               Date departureDate, int requestTrainId, String locale) {
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
                        trainStationService.fillTrainStation(trainStation, locale);
                        fullTrainInfoDto.setFirstRootStation(trainStation.getStation());
                    });
            trainStationDao.getLastRootByTrainId(requestTrainId)
                    .ifPresent(trainStation -> {
                        trainStationService.fillTrainStation(trainStation, locale);
                        fullTrainInfoDto.setLastRootStation(trainStation.getStation());
                    });
        }

        fullTrainInfoDto.setDuration(
                TimeDateUtil.getTimeDiff(fullTrainInfoDto.getDepartureTime(), fullTrainInfoDto.getArrivalTime()));

        fullTrainInfoDto.setTrainCoachPlacesInfoDtoList(
                trainCoachPlacesInfoFacade.getTrainCoachPlacesInformation(requestTrainId, departureStationId,
                        destinationStationId, departureDate, locale));

        LOG.info(String.format("Prepare information about train #%d", requestTrainId));
        return fullTrainInfoDto;
    }
}
