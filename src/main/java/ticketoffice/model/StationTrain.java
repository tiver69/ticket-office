package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class StationTrain {

    private Station station;
    private Train train;
    private int order;
    private int prise;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public StationTrain() {
    }

    public StationTrain(Station station, Train train,
                        int order, int prise, LocalTime arrivalTime, LocalTime departureTime) {
        this.station = station;
        this.train = train;
        this.order = order;
        this.prise = prise;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StationTrain)) return false;
        StationTrain otherStationTrain = (StationTrain)obj;
        return station.equals(otherStationTrain.getStation())
                && train.equals(otherStationTrain.getTrain())
                && (order == otherStationTrain.getOrder())
                && (prise == otherStationTrain.getPrise())
                && arrivalTime.equals(otherStationTrain.getArrivalTime())
                && departureTime.equals(otherStationTrain.getDepartureTime());
    }

    @Override
    public int hashCode() {
        int result = station.hashCode() + train.hashCode();
        result = 31 * result + order;
        result = 31 * result + prise;
        return result;
    }
}
