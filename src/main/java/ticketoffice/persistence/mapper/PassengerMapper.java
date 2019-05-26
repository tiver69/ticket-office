package ticketoffice.persistence.mapper;

import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengerMapper implements Mapper<Passenger> {

    private PassengerDao passengerDao;

    public void setDao(AbstractDao passengerDao) {
        this.passengerDao = (PassengerDao)passengerDao;
    }

    @Override
    public Passenger extractItem(ResultSet resultSet) throws SQLException {
        Passenger passenger = new Passenger();
        while (resultSet.next()) {
            passenger.setId(resultSet.getInt("id"));
            passenger.setLastName(resultSet.getString("last_name"));
            passenger.setFirstName(resultSet.getString("first_name"));
            passenger.setLogin(resultSet.getString("login"));
            passenger.setPassword("");
        }
        return passenger;
    }

    @Override
    public ArrayList<Passenger> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<Passenger> trainList = new ArrayList<>();
        while (resultSet.next()) {
            Passenger passenger
                    = passengerDao.getById(resultSet.getInt("id"));
            trainList.add(passenger);
        }
        return trainList;
    }

    public Passenger extractItemFromRequest(HttpServletRequest request){
        Passenger passenger = new Passenger();

        passenger.setFirstName(request.getParameter("firstName"));
        passenger.setLastName(request.getParameter("lastName"));
        passenger.setLogin(request.getParameter("login"));
        passenger.setPassword(request.getParameter("password")); //ADD HASH HERE

        return passenger;
    }
}
