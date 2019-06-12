package ticketoffice.dto.train;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Station;
import ticketoffice.model.Train;

import java.sql.Time;

/**
 * Base entity for keeping information about train and it's root:
 *  first stations for current train (order = 0),
 *  last station for current train (order = last),
 *  departure time from requested departure station,
 *  arrival time for requested destination station,
 *  duration of root from requested departure to destination stations.
 */
@Setter
@Getter
@NoArgsConstructor
public class TrainInfoDto {
    private Train train;
    private Station firstRootStation;
    private Station lastRootStation;

    private Time departureTime;
    private Time arrivalTime;

    private Time duration;
}
