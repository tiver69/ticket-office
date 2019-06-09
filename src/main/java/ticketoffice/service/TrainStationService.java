package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;
import ticketoffice.persistence.dao.interfaces.TrainStationDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainStationService {

    private static Logger LOG = Logger.getLogger(TrainStationService.class);

    public TrainStation getTrainStation(int stationId, int trainId) throws ValidateFailException {
        try (TrainStationDao trainStationDao = DaoFactory.getInstance().getTrainStationDao()) {
            Optional<TrainStation> trainStation = trainStationDao.getByTrainIdAndStationId(
                    stationId, trainId);
            if (trainStation.isPresent())
                return trainStation.get();
        }

        LOG.error(String.format("Station #%d int train #%d root doesn't exist",
                stationId, trainId));
        throw new ValidateFailException("root");
    }

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
        return getTrainStation(stationId, trainId).getOrder();
    }
}
