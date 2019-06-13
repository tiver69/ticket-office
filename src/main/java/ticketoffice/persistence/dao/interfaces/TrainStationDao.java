package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.TrainStation;

import java.util.List;
import java.util.Optional;

/**
 * Interface for sql-queries in TrainStation table.
 */
public interface TrainStationDao extends AbstractDao<TrainStation> {

    /**
     * Extract single item from db according to given Station and Train IDs.
     *
     * @param stationId -   int variable with requested station ID
     * @param trainId   -   int variable with requested train ID
     * @return Optional of TrainStation if value was successfully extracted, Optional of Nullable
     * if there was no record in db
     */
    Optional<TrainStation> getByTrainIdAndStationId(int stationId, int trainId);

    /**
     * Extract single item from db according to given station Order and Train ID.
     *
     * @param order   -   int variable with requested order of station
     * @param trainId -   int variable with requested train ID
     * @return Optional of TrainStation if value was successfully extracted, Optional of Nullable
     * if there was no record in db
     */
    Optional<TrainStation> getByTrainIdAndOrder(int trainId, int order);

    /**
     * Extract single item from db according to given Train ID
     * and auto-found number of last Station order in train root.
     *
     * @param trainId -   int variable with requested train ID
     * @return Optional of TrainStation if value was successfully extracted, Optional of Nullable
     * if there was no record in db
     */
    Optional<TrainStation> getLastRootByTrainId(int trainId);

    /**
     * Extract List of items from db which have specified Train(by ID) value.
     *
     * @param trainId -   int variable with requested train ID
     * @return List of Train Stations.
     */
    List<TrainStation> getFullRootByTrainId(int trainId);
}
