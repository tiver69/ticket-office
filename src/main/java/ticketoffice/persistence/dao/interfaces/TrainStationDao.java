package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.TrainStation;

import java.util.Optional;

public interface TrainStationDao extends AbstractDao<TrainStation> {

    Optional<TrainStation> getByTrainIdAndStationId(int stationId, int trainId);

    Optional<TrainStation> getByTrainIdAndOrder(int trainId, int order);

    Optional<TrainStation> getLastRootByTrainId(int trainId);

}
