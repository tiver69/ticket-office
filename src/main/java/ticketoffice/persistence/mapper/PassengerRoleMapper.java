package ticketoffice.persistence.mapper;

import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengerRoleMapper implements Mapper<PassengerRole> {

    private PassengerRoleDao passengerRoleDao;

    public void setDao(AbstractDao passengerDao) {
        this.passengerRoleDao = (PassengerRoleDao) passengerDao;
    }

    @Override
    public PassengerRole extractItem(ResultSet resultSet) throws SQLException {
        PassengerRole passengerRole = new PassengerRole();
        Passenger passenger = new Passenger();
        while (resultSet.next()) {
            passengerRole.setId(resultSet.getInt("id"));
            passenger.setId(resultSet.getInt("passenger_id"));
            passengerRole.setPassenger(passenger);
            passengerRole.setRole(
                    Role.valueOf(resultSet.getString("role")));
        }
        return passengerRole;
    }

    @Override
    public ArrayList<PassengerRole> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<PassengerRole> passengerRoleList = new ArrayList<>();
        while (resultSet.next()) {
            PassengerRole passengerRole
                    = passengerRoleDao.getById(resultSet.getInt("id"));
            passengerRoleList.add(passengerRole);
        }
        return passengerRoleList;
    }
}
