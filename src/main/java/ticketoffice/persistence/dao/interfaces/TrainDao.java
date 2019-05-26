package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Train;

import java.util.List;

public interface TrainDao extends AbstractDao<Train> {

    List<Train> getTrainInDirection(int departureStationId, int destinationStationId);
}
