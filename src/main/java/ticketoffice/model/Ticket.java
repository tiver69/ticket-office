package ticketoffice.model;

import lombok.*;
import ticketoffice.model.builders.TicketBuilder;

import java.sql.Date;

/**
 * Entity for keeping Ticket information, contains information:
 * passenger  -   Passenger item as owner of ticket,
 * departureStation/destinationStation    -   Station value of depatrure/destination,
 * date    -   Date value of departure date,
 * TrainCoach  -   TrainCoach item related to specific coach in train,
 * place   -   int value of place number in coach,
 * price   -   int value of ticket total price.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private int id;
    private Passenger passenger;
    private Station departureStation;
    private Station destinationStation;
    private Date date;
    private TrainCoach trainCoach;
    private int place;
    private int price;

    public Ticket(TicketBuilder ticketBuilder) {
        this.id = ticketBuilder.getId();
        this.passenger = ticketBuilder.getPassenger();
        this.departureStation = ticketBuilder.getDepartureStation();
        this.destinationStation = ticketBuilder.getDestinationStation();
        this.date = ticketBuilder.getDate();
        this.trainCoach = ticketBuilder.getTrainCoach();
        this.place = ticketBuilder.getPlace();
        this.price = ticketBuilder.getPrice();
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
