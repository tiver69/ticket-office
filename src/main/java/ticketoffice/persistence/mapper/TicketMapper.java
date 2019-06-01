package ticketoffice.persistence.mapper;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;
import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainCoach;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketMapper implements Mapper<Ticket> {

    private static Logger LOG = Logger.getLogger(TicketMapper.class);
    private static int HOUR_DIFFERENCE = 60 * 60 * 1000;
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
            ticket.setDate(new Date(resultSet.getDate("departure_date").getTime() - HOUR_DIFFERENCE));
            trainCoach.setId(resultSet.getInt("coach_id"));
            ticket.setTrainCoach(trainCoach);
            ticket.setPlace(resultSet.getInt("place"));
            ticket.setPrice(resultSet.getInt("price"));
        }
        return ticket;
    }

    @Override
    public ArrayList<Ticket> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        while (resultSet.next()) {
            ticketDao.getById(resultSet.getInt("id"))
                    .map(ticketList::add);
        }
        return ticketList;
    }

    public Ticket extractItemFromRequest(HttpServletRequest request) {
        Ticket ticket = new Ticket();

        UserDto user = (UserDto) request.getSession().getAttribute("user");
        int departureStationId = Integer.parseInt(request.getParameter("departureStationHidden"));
        int destinationStationId = Integer.parseInt(request.getParameter("destinationStationHidden"));
        int selectedCoachId = Integer.parseInt(request.getParameter("selectedCoach"));
        int selectedPlace = Integer.parseInt(request.getParameter("selectedPlace"));
        Date date = Date.valueOf(request.getParameter("departureDateHidden"));

        Station departureStation = new Station();
        Station destinationStation = new Station();
        TrainCoach trainCoach = new TrainCoach();

        ticket.setPassenger(user.getPassenger());
        departureStation.setId(departureStationId);
        ticket.setDepartureStation(departureStation);
        destinationStation.setId(destinationStationId);
        ticket.setDestinationStation(destinationStation);
        ticket.setDate(date);
        trainCoach.setId(selectedCoachId);
        ticket.setTrainCoach(trainCoach);
        ticket.setPlace(selectedPlace);

        LOG.debug(String.format("Detail information request for ticket #%s train, #%s coach(id=%d), #%d place",
                request.getParameter("selectedTrainId"),
                request.getParameter("currentCoach"), selectedCoachId, selectedPlace));
        return ticket;
    }
}
