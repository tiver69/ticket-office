package ticketoffice.model.builders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainCoach;

import java.sql.Date;

/**
 * Builder for Ticket entity
 *
 * @see Ticket
 */
@Getter
@NoArgsConstructor
public class TicketBuilder {

    private int id;
    private Passenger passenger;
    private Station departureStation;
    private Station destinationStation;
    private Date date;
    private TrainCoach trainCoach;
    private int place;
    private int price;

    public TicketBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TicketBuilder setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public TicketBuilder setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
        return this;
    }

    public TicketBuilder setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
        return this;
    }

    public TicketBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public TicketBuilder setTrainCoach(TrainCoach trainCoach) {
        this.trainCoach = trainCoach;
        return this;
    }

    public TicketBuilder setPlace(int place) {
        this.place = place;
        return this;
    }

    public TicketBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    public Ticket build() {
        return new Ticket(this);
    }
}
