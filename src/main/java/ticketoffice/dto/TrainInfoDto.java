package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.Train;

import java.sql.Time;
import java.time.LocalTime;

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
