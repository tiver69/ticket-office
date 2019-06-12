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

    /**
     * @return extracted TrainStation with requested locale parameter from Optional value.
     * @throws ValidateFailException in case item was not found.
     */
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

    /**
     * Get all additional information for TrainStation fields and set extracted values to
     * request trainStation parameter.
     */
    public void fillTrainStation(TrainStation trainStation, String locale) {
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationDao.setLocale(locale);
            stationDao.getById(trainStation.getStation().getId())
                    .ifPresent(trainStation::setStation);
        }

        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()) {
            trainDao.getById(trainStation.getTrain().getId())
                    .ifPresent(trainStation::setTrain);
        }
    }

    /**
     * @return List of TrainStation items related to requested Train and with order value in range
     * of departureStation and destinationStationId order.
     */
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

    /**
     * @return extracted trainStation Order.
     */
    public int getTrainStationOrder(int stationId, int trainId) {
        return getTrainStation(stationId, trainId).getOrder();
    }
}
