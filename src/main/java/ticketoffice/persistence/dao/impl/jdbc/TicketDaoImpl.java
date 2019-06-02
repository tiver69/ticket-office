package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.mapper.TicketMapper;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class TicketDaoImpl extends AbstractDaoImpl<Ticket> implements TicketDao {

    private String INSERT = "INSERT INTO tickets (id,passenger_id, departure_station_id,destination_station_id," +
            "departure_date,coach_id,place,price) VALUES (?,?,?,?,?,?,?,?)";
    private String GET_BY_ID = "SELECT * FROM tickets WHERE id = ?";
    private String GET_ALL = "SELECT * FROM tickets";
    private String DELETE = "DELETE FROM tickets WHERE id=?";
    private String UPDATE = "UPDATE tickets SET passenger_id=?, departure_station_id=?, " +
            "destination_station_id=?, departure_date=?, coach_id=?, place=?, price=? WHERE id=?";
    private String GET_BY_COACH_ID_AND_DATE = "SELECT * FROM tickets WHERE coach_id=? AND departure_date=?";
    private String GET_BY_PASSENGER_ID = "SELECT * FROM tickets WHERE passenger_id=?";

    public TicketDaoImpl(Connection connection) {
        super(connection, new TicketMapper());
        LOG = Logger.getLogger(TicketDaoImpl.class);
    }

    @Override
    public int create(Ticket ticket) {
        return create(INSERT, statement -> {
            statement.setInt(1, ticket.getId());
            statement.setInt(2, ticket.getPassenger().getId());
            statement.setInt(3, ticket.getDepartureStation().getId());
            statement.setInt(4, ticket.getDestinationStation().getId());
            statement.setDate(5, new Date(ticket.getDate().getTime() + 24 * 60 * 60 * 1000));
            statement.setInt(6, ticket.getTrainCoach().getId());
            statement.setInt(7, ticket.getPlace());
            statement.setInt(8, ticket.getPrice());
        });
    }

    @Override
    public Optional<Ticket> getById(int id) {
        return getById(GET_BY_ID, statement -> {
            statement.setInt(1, id);
        });
    }

    @Override
    public boolean delete(int id) {
        return delete(DELETE, statement -> {
            statement.setInt(1, id);
        });
    }

    @Override
    public boolean update(Ticket ticket) {
        return update(UPDATE, statement -> {
            statement.setInt(1, ticket.getPassenger().getId());
            statement.setInt(2, ticket.getDepartureStation().getId());
            statement.setInt(3, ticket.getDestinationStation().getId());
            statement.setDate(4, new Date(ticket.getDate().getTime() + 24 * 60 * 60 * 1000));
            statement.setInt(5, ticket.getTrainCoach().getId());
            statement.setInt(6, ticket.getPlace());
            statement.setInt(7, ticket.getPrice());
            statement.setInt(8, ticket.getId());
        });
    }

    @Override
    public List<Ticket> getAll() {
        return getAll(GET_ALL, statement -> {
        });
    }

    @Override
    public List<Ticket> getTicketsByCoachIdAndDate(int coachId, Date date) {
        return getAll(GET_BY_COACH_ID_AND_DATE, statement -> {
            statement.setInt(1, coachId);
            statement.setDate(2, new Date(date.getTime() + 24 * 60 * 60 * 1000));
        });
    }

    @Override
    public List<Ticket> getTicketsByPassengerId(int passengerId) {
        return getAll(GET_BY_PASSENGER_ID, statement -> {
            statement.setInt(1, passengerId);
        });
    }

}
