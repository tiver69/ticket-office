package ticketoffice.persistence.mapper;

import org.apache.commons.codec.digest.DigestUtils;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mapper for Passenger class, for extraction Passenger item from db result set
 */
public class PassengerMapper implements Mapper<Passenger> {

    private PassengerDao passengerDao;

    public void setDao(AbstractDao passengerDao) {
        this.passengerDao = (PassengerDao) passengerDao;
    }

    @Override
    public Optional<Passenger> extractItem(ResultSet resultSet) throws SQLException {
        Passenger passenger = null;
        if (resultSet.first()) {
            passenger = new Passenger();
            passenger.setId(resultSet.getInt("id"));
            passenger.setLastName(resultSet.getString("last_name"));
            passenger.setFirstName(resultSet.getString("first_name"));
            passenger.setLogin(resultSet.getString("login"));
            passenger.setPassword(resultSet.getString("password"));
        }
        return Optional.ofNullable(passenger);
    }

    @Override
    public ArrayList<Passenger> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Passenger> trainList = new ArrayList<>();
        while (resultSet.next()) {
            passengerDao.getById(resultSet.getInt("id"))
                    .ifPresent(trainList::add);
        }
        return trainList;
    }

    public Passenger extractItemFromRequest(HttpServletRequest request) {
        Passenger passenger = new Passenger();

        passenger.setFirstName(request.getParameter("firstName"));
        passenger.setLastName(request.getParameter("lastName"));
        passenger.setLogin(request.getParameter("login"));
        passenger.setPassword(
                DigestUtils.md5Hex(
                        request.getParameter("password")));

        return passenger;
    }
}
