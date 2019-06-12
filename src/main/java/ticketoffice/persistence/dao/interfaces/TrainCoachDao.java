package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.TrainCoach;

import java.util.List;

/**
 * Interface for sql-queries in TrainCoach table.
 */
public interface TrainCoachDao extends AbstractDao<TrainCoach> {

    /**
     * Extract List of items from db related to Train with specified ID.
     *
     * @param trainId -   int variable of requested train ID,
     * @return List of Train Coaches.
     */
    List<TrainCoach> getByTrainId(int trainId);

}
