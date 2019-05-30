package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.model.Train;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.TrainDao;
import ticketoffice.service.utils.TimeDateUtil;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    private static Logger LOG = Logger.getLogger(TrainService.class);

    public List<Train> findTrainInDirection(int departureStationId, int destinationStationId, Date departureDate){
        List<Train> resultTrains;
        try (TrainDao trainDao = DaoFactory.getInstance().getTrainDao()){
            resultTrains = trainDao.getTrainInDirection(departureStationId, destinationStationId);
        }

        int dayOfYear = TimeDateUtil.getDayOfYearOfDate(departureDate);
        resultTrains = resultTrains.stream().filter(train -> dayOfYear%train.getFrequency() == 0)
                .collect(Collectors.toList());

        LOG.info(String.format("Search for trains between %d -> %d on %s; Found %d result(s)",
                departureStationId, destinationStationId, departureDate.toString(), resultTrains.size()));

        return resultTrains;
    }
}
