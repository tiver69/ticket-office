package ticketoffice.persistence.mapper;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;
import ticketoffice.model.Passenger;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainCoach;
import ticketoffice.model.builders.TicketBuilder;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mapper for Ticket class, for extraction Ticket item from db result set.
 * Fills only id-field for related Passenger, Station, TrainCoach value.
 */
public class TicketMapper implements Mapper<Ticket> {

    private static Logger LOG = Logger.getLogger(TicketMapper.class);
    private static int HOUR_DIFFERENCE = 60 * 60 * 1000;
    private TicketDao ticketDao;

    public void setDao(AbstractDao abstractDao) {
        this.ticketDao = (TicketDao) abstractDao;
    }

    @Override
    public Optional<Ticket> extractItem(ResultSet resultSet) throws SQLException {
        Ticket ticket = null;
        Passenger passenger = new Passenger();
        Station departureStation = new Station();
        Station destinationStation = new Station();
        TrainCoach trainCoach = new TrainCoach();
        if (resultSet.first()) {
            passenger.setId(resultSet.getInt("passenger_id"));
            departureStation.setId(resultSet.getInt("departure_station_id"));
            destinationStation.setId(resultSet.getInt("destination_station_id"));
            trainCoach.setId(resultSet.getInt("coach_id"));

            TicketBuilder ticketBuilder = new TicketBuilder();
            ticketBuilder.setId(resultSet.getInt("id"))
                    .setPassenger(passenger)
                    .setDepartureStation(departureStation)
                    .setDestinationStation(destinationStation)
                    .setDate(new Date(resultSet.getDate("departure_date").getTime() - HOUR_DIFFERENCE))
                    .setTrainCoach(trainCoach)
                    .setPlace(resultSet.getInt("place"))
                    .setPrice(resultSet.getInt("price"));
            ticket = ticketBuilder.build();
        }
        return Optional.ofNullable(ticket);
    }

    @Override
    public ArrayList<Ticket> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        while (resultSet.next()) {
            ticketDao.getById(resultSet.getInt("id"))
                    .ifPresent(ticketList::add);
        }
        return ticketList;
    }

    public Ticket extractItemFromRequest(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        int departureStationId = Integer.parseInt(request.getParameter("departureStationHidden"));
        int destinationStationId = Integer.parseInt(request.getParameter("destinationStationHidden"));
        int selectedCoachId = Integer.parseInt(request.getParameter("selectedCoach"));
        int selectedPlace = Integer.parseInt(request.getParameter("selectedPlace"));
        Date date = Date.valueOf(request.getParameter("departureDateHidden"));

        Station departureStation = new Station();
        departureStation.setId(departureStationId);
        Station destinationStation = new Station();
        destinationStation.setId(destinationStationId);
        TrainCoach trainCoach = new TrainCoach();
        trainCoach.setId(selectedCoachId);

        TicketBuilder ticketBuilder = new TicketBuilder();
        ticketBuilder.setPassenger(user.getPassenger())
                .setDepartureStation(departureStation)
                .setDestinationStation(destinationStation)
                .setDate(date)
                .setTrainCoach(trainCoach)
                .setPlace(selectedPlace);
        Ticket ticket = ticketBuilder.build();

        LOG.debug(String.format("Detail information request for ticket #%s train, #%s coach(id=%d), #%d place",
                request.getParameter("selectedTrainId"),
                request.getParameter("currentCoach"), selectedCoachId, selectedPlace));
        return ticket;
    }
}
