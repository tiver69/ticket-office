package ticketoffice.facade.train;

import org.apache.log4j.Logger;
import ticketoffice.dto.train.ShortTrainInfoDto;
import ticketoffice.facade.coach.CoachTypePlacesInfoFacade;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;
import ticketoffice.service.TrainService;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for creating ShortTrainInfoDto objects for each
 * coach type of requested train
 */
public class ShortTrainInfoFacade {

    private static Logger LOG = Logger.getLogger(ShortTrainInfoFacade.class);
    private TrainService trainService = new TrainService();
    private TrainStationService trainStationService = new TrainStationService();
    private CoachTypePlacesInfoFacade coachTypePlacesInfoFacade = new CoachTypePlacesInfoFacade();

    /**
     * Load information about each of coaches types in requested train
     *
     * @param departureStationId   -   db id of requested departure station
     * @param destinationStationId -   db id of requested destination station
     * @param departureDate        -   request value of date
     * @param locale               -   current session locale parameter
     * @return List of ShortTrainInfoDto items related to each coach type
     */
    public List<ShortTrainInfoDto> getRequestTrainInformation
    (int departureStationId, int destinationStationId, Date departureDate, String locale) {

        List<ShortTrainInfoDto> shortTrainInfoDtoList = new ArrayList<>();
        List<Train> requestTrainList =
                trainService.findTrainInDirection(departureStationId, destinationStationId, departureDate);

        requestTrainList.forEach(train -> {
            ShortTrainInfoDto shortTrainInfoDto = new ShortTrainInfoDto();

            shortTrainInfoDto.setTrain(train);

            shortTrainInfoDto.setDepartureTime(trainStationService.getTrainStation(departureStationId, train.getId()).getDepartureTime());
            shortTrainInfoDto.setArrivalTime(trainStationService.getTrainStation(destinationStationId, train.getId()).getArrivalTime());

            try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {

                trainStationDao.getByTrainIdAndOrder(train.getId(), 0)
                        .ifPresent(trainStation -> {
                            trainStationService.fillTrainStation(trainStation, locale);
                            shortTrainInfoDto.setFirstRootStation(trainStation.getStation());
                        });
                trainStationDao.getLastRootByTrainId(train.getId())
                        .ifPresent(trainStation -> {
                            trainStationService.fillTrainStation(trainStation, locale);
                            shortTrainInfoDto.setLastRootStation(trainStation.getStation());
                        });
            }

            shortTrainInfoDto.setDuration(
                    TimeDateUtil.getTimeDiff(shortTrainInfoDto.getDepartureTime(), shortTrainInfoDto.getArrivalTime()));

            shortTrainInfoDto.setCoachTypePlacesInfoDtoList(
                    coachTypePlacesInfoFacade.getTrainPlacesInformation(train.getId(), departureStationId, destinationStationId, departureDate, locale));

            shortTrainInfoDtoList.add(shortTrainInfoDto);
        });

        LOG.info(String.format("Prepare information about %d trains",
                shortTrainInfoDtoList.size()));
        return shortTrainInfoDtoList;
    }

}
