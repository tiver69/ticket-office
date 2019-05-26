package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Ticket {

    private int id;
    private Passenger passenger;
    private Station departureStation;
    private Station destinationStation;
    private Date date;
    private TrainCoach trainCoach;
    private int place;

    public Ticket() {
    }

    public Ticket(int id, Passenger passenger, Station departureStation,
                  Station destinationStation, Date date, TrainCoach trainCoach, int place) {
        this.id = id;
        this.passenger = passenger;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.date = date;
        this.trainCoach = trainCoach;
        this.place = place;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ticket)) return false;
        Ticket otherTicket = (Ticket) obj;
        return id == otherTicket.getId()
                && passenger.equals(otherTicket.getPassenger())
                && departureStation.equals(otherTicket.getDepartureStation())
                && destinationStation.equals(otherTicket.getDestinationStation())
                && date.equals(otherTicket.getDate())
                && trainCoach.equals(otherTicket.getTrainCoach())
                && (place == otherTicket.getPlace());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + passenger.hashCode();
        result = 31 * result + departureStation.hashCode();
        result = 31 * result + destinationStation.hashCode();
        result = 31 * result + place;
        return result;
    }
}
