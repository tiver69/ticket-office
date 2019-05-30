package ticketoffice.model;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainStation {

    private Station station;
    private Train train;
    private int order;
    private int prise;
    private Time arrivalTime;
    private Time departureTime;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TrainStation)) return false;
        TrainStation otherTrainStation = (TrainStation)obj;
        return station.equals(otherTrainStation.getStation())
                && train.equals(otherTrainStation.getTrain())
                && (order == otherTrainStation.getOrder())
                && (prise == otherTrainStation.getPrise())
                && arrivalTime.equals(otherTrainStation.getArrivalTime())
                && departureTime.equals(otherTrainStation.getDepartureTime());
    }

    @Override
    public int hashCode() {
        int result = station.hashCode() + train.hashCode();
        result = 31 * result + order;
        result = 31 * result + prise;
        return result;
    }
}
