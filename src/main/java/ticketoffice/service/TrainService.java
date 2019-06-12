package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainDao;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainService {

    private static Logger LOG = Logger.getLogger(TrainService.class);

    /**
     * @return extracted Train with requested locale parameter from Optional value.
     * @throws ValidateFailException in case item was not found.
     */
    public Train getTrain(int trainId) throws ValidateFailException {
        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()) {
            Optional<Train> train = trainDao.getById(trainId);
            if (train.isPresent())
                return train.get();
        }

        LOG.error(String.format("Train with id#%d doesn't exist", trainId));
        throw new ValidateFailException("train");
    }

    /**
     * Extract all trains in requested direction(from departureStation to destinationStation) and check
     * if found train travels in requested day, according to frequency value.
     *
     * @return List of available trains.
     */
    public List<Train> findTrainInDirection(int departureStationId, int destinationStationId, Date departureDate) {
        List<Train> resultTrains;
        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()) {
            resultTrains = trainDao.getTrainInDirection(departureStationId, destinationStationId);
        }

        int dayOfYear = TimeDateUtil.getDayOfYearOfDate(departureDate);
        resultTrains = resultTrains.stream().filter(train -> dayOfYear % train.getFrequency() == 0)
                .collect(Collectors.toList());

        LOG.info(String.format("Search for trains between %d -> %d on %s; Found %d result(s)",
                departureStationId, destinationStationId, departureDate.toString(), resultTrains.size()));

        return resultTrains;
    }
}
