package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;

import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainStationService {

    private static Logger LOG = Logger.getLogger(TrainStationService.class);

    public void fillTrainStation(TrainStation trainStation) {
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationDao.getById(trainStation.getStation().getId())
                    .ifPresent(trainStation::setStation);
        }

        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()) {
            trainDao.getById(trainStation.getTrain().getId())
                    .ifPresent(trainStation::setTrain);
        }
    }

    public Time getArrivalTime(int destinationStationId, int requestTrainId) {
        try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
            Optional<TrainStation> destination = trainStationDao.getByTrainIdAndStationId(
                    destinationStationId, requestTrainId);
            if (destination.isPresent()) {
                return destination.get().getArrivalTime();
            } else {
                LOG.error("TrainStation doesn't exist");
                throw new IllegalArgumentException("TrainStation doesn't exist"); //change exception
            }
        }
    }

    public Time getDepartureTime(int destinationStationId, int requestTrainId) {
        try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
            Optional<TrainStation> destination = trainStationDao.getByTrainIdAndStationId(
                    destinationStationId, requestTrainId);
            if (destination.isPresent()) {
                return destination.get().getDepartureTime();
            } else {
                LOG.error("TrainStation doesn't exist");
                throw new IllegalArgumentException("TrainStation doesn't exist"); //change exception
            }
        }
    }

    public List<TrainStation> getTrainStationRoot(int departureStationId, int destinationStationId, int trainId) {
        List<TrainStation> fullRoot;
        try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
            fullRoot = trainStationDao.getFullRootByTrainId(trainId);
        }
        LOG.debug("Full root for train #" + trainId + ":" + fullRoot.toString());

        int departureStationOrder = fullRoot.stream()
                .filter(trainStation -> trainStation.getStation().getId() == departureStationId)
                .findFirst().get().getOrder();
        int destinationStationOrder = fullRoot.stream()
                .filter(trainStation -> trainStation.getStation().getId() == destinationStationId)
                .findFirst().get().getOrder();
        fullRoot = fullRoot.stream().filter(trainStation -> trainStation.getOrder() > departureStationOrder &&
                trainStation.getOrder() <= destinationStationOrder).collect(Collectors.toList());

        LOG.info(String.format("Found %d station in requested root between #%d(order - %d) and #%d(order - %d)",
                fullRoot.size(), departureStationId, departureStationOrder,
                destinationStationId, destinationStationOrder));
        return fullRoot;
    }

    public int getTrainStationOrder(int stationId, int trainId) {
        try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
            Optional<TrainStation> trainStation = trainStationDao.getByTrainIdAndStationId(stationId, trainId);
            if (trainStation.isPresent()) {
                return trainStation.get().getOrder();
            } else throw new IllegalArgumentException("Train-Station doesn't exist");//change exception
        }
    }
}
