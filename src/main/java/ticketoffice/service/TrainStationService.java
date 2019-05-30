package ticketoffice.service;

import ticketoffice.model.TrainStation;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

public class TrainStationService {

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
}
