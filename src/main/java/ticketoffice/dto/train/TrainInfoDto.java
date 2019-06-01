package ticketoffice.dto.train;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Station;
import ticketoffice.model.Train;

import java.sql.Time;

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
