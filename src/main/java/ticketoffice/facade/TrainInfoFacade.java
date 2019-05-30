package ticketoffice.facade;

import ticketoffice.dto.TrainInfoDto;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;
import ticketoffice.service.TrainService;
import ticketoffice.service.TrainStationService;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TrainInfoFacade {

    private TrainService trainService = new TrainService();
    private TrainStationService trainStationService = new TrainStationService();

    public List<TrainInfoDto> getRequestTrainInformation
            (int departureStationId, int destinationStationId, Date departureDate) {

        List<Train> requestTrainList =
                trainService.findTrain(departureStationId, destinationStationId, departureDate);
        List<TrainInfoDto> trainInfoDtoList = new ArrayList<>();

        requestTrainList.forEach(train -> {
            TrainInfoDto trainInfoDto = new TrainInfoDto();

            trainInfoDto.setTrain(train);

            try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
                trainStationDao.getByTrainIdAndStationId(
                        departureStationId, train.getId()).ifPresent(trainStation -> {
                    trainInfoDto.setDepartureTime(trainStation.getDepartureTime());
                });
                trainStationDao.getByTrainIdAndStationId(
                        destinationStationId, train.getId()).ifPresent(trainStation -> {
                    trainInfoDto.setArrivalTime(trainStation.getArrivalTime());
                });

                trainStationDao.getByTrainIdAndOrder(train.getId(), 0)
                        .ifPresent(trainStation -> {
                            trainStationService.fillTrainStation(trainStation);
                            trainInfoDto.setFirstRootStation(trainStation.getStation());
                        });
                trainStationDao.getLastRootByTrainId(train.getId())
                        .ifPresent(trainStation -> {
                            trainStationService.fillTrainStation(trainStation);
                            trainInfoDto.setLastRootStation(trainStation.getStation());
                        });
            }

            trainInfoDto.setDuration(
                    TimeDateUtil.getTimeDiff(trainInfoDto.getDepartureTime(), trainInfoDto.getArrivalTime()));

            trainInfoDtoList.add(trainInfoDto);
        });
        return trainInfoDtoList;
    }

}
