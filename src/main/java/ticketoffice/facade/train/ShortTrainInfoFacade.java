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

public class ShortTrainInfoFacade {

    private static Logger LOG = Logger.getLogger(ShortTrainInfoFacade.class);
    private TrainService trainService = new TrainService();
    private TrainStationService trainStationService = new TrainStationService();
    private CoachTypePlacesInfoFacade coachTypePlacesInfoFacade = new CoachTypePlacesInfoFacade();

    public List<ShortTrainInfoDto> getRequestTrainInformation
            (int departureStationId, int destinationStationId, Date departureDate) {

        List<ShortTrainInfoDto> shortTrainInfoDtoList = new ArrayList<>();
        List<Train> requestTrainList =
                trainService.findTrainInDirection(departureStationId, destinationStationId, departureDate);

        requestTrainList.forEach(train -> {
            ShortTrainInfoDto shortTrainInfoDto = new ShortTrainInfoDto();

            shortTrainInfoDto.setTrain(train);

            shortTrainInfoDto.setDepartureTime(trainStationService.getDepartureTime(departureStationId, train.getId()));
            shortTrainInfoDto.setArrivalTime(trainStationService.getArrivalTime(destinationStationId, train.getId()));

            try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {

                trainStationDao.getByTrainIdAndOrder(train.getId(), 0)
                        .ifPresent(trainStation -> {
                            trainStationService.fillTrainStation(trainStation);
                            shortTrainInfoDto.setFirstRootStation(trainStation.getStation());
                        });
                trainStationDao.getLastRootByTrainId(train.getId())
                        .ifPresent(trainStation -> {
                            trainStationService.fillTrainStation(trainStation);
                            shortTrainInfoDto.setLastRootStation(trainStation.getStation());
                        });
            }

            shortTrainInfoDto.setDuration(
                    TimeDateUtil.getTimeDiff(shortTrainInfoDto.getDepartureTime(), shortTrainInfoDto.getArrivalTime()));

            shortTrainInfoDto.setCoachTypePlacesInfoDtoList(
                    coachTypePlacesInfoFacade.getTrainPlacesInformation(train.getId(), departureDate));

            shortTrainInfoDtoList.add(shortTrainInfoDto);
        });

        LOG.info(String.format("Prepare information about %d trains",
                shortTrainInfoDtoList.size()));
        return shortTrainInfoDtoList;
    }

}