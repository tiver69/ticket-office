package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Train;

import java.util.List;

/**
 * Interface for sql-queries in Train table.
 */
public interface TrainDao extends AbstractDao<Train> {

    /**
     * Extract List of items from db which have specified Stations(by ID) in their root.
     *
     * @param departureStationId   -   int variable of requested departure Station ID,
     * @param destinationStationId -   int variable of requested destination Station ID,
     * @return List of Trains.
     */
    List<Train> getTrainInDirection(int departureStationId, int destinationStationId);
}
