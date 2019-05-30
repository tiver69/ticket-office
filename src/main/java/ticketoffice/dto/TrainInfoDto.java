package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Station;
import ticketoffice.model.Train;

import java.sql.Time;
import java.util.List;

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

    private List<CoachPlacesInfoDto> coachPlacesInfo;
}
