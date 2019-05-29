package ticketoffice.persistence.mapper;

import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketMapper implements Mapper<Ticket> {

    private TicketDao ticketDao;

    public void setDao(AbstractDao abstractDao) {
        this.ticketDao = (TicketDao) abstractDao;
    }

    @Override
    public Ticket extractItem(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        Passenger passenger = new Passenger();
        Station departureStation = new Station();
        Station destinationStation = new Station();
        TrainCoach trainCoach = new TrainCoach();
        while (resultSet.next()) {
            ticket.setId(resultSet.getInt("id"));
            passenger.setId(resultSet.getInt("passenger_id"));
            ticket.setPassenger(passenger);
            departureStation.setId(resultSet.getInt("departure_station_id"));
            ticket.setDepartureStation(departureStation);
            destinationStation.setId(resultSet.getInt("destination_station_id"));
            ticket.setDestinationStation(destinationStation);
            ticket.setDate(resultSet.getDate("departure_date"));
            trainCoach.setId(resultSet.getInt("coach_id"));
            ticket.setTrainCoach(trainCoach);
            ticket.setPlace(resultSet.getInt("place"));
        }
        return ticket;
    }

    @Override
    public ArrayList<Ticket> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        while (resultSet.next()) {
            ticketDao.getById(resultSet.getInt("id"))
                    .map(ticketList::add);
//            Ticket ticket
//                    = ticketDao.getById(resultSet.getInt("id")).get();
//            ticketList.add(ticket);
        }
        return ticketList;
    }
}
