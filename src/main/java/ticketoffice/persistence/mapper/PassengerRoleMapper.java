package ticketoffice.persistence.mapper;

import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Mapper for PassengerRole class, for extraction PassengerRole item from db result set.
 * Fills only id-field for related Passenger value.
 */
public class PassengerRoleMapper implements Mapper<PassengerRole> {

    private PassengerRoleDao passengerRoleDao;

    public void setDao(AbstractDao passengerDao) {
        this.passengerRoleDao = (PassengerRoleDao) passengerDao;
    }

    @Override
    public Optional<PassengerRole> extractItem(ResultSet resultSet) throws SQLException {
        PassengerRole passengerRole = null;
        Passenger passenger = new Passenger();
        if (resultSet.first()) {
            passengerRole = new PassengerRole();
            passengerRole.setId(resultSet.getInt("id"));
            passenger.setId(resultSet.getInt("passenger_id"));
            passengerRole.setPassenger(passenger);
            passengerRole.setRole(
                    Role.valueOf(resultSet.getString("role")));
        }
        return Optional.ofNullable(passengerRole);
    }

    @Override
    public ArrayList<PassengerRole> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<PassengerRole> passengerRoleList = new ArrayList<>();
        while (resultSet.next()) {
            passengerRoleDao.getById(resultSet.getInt("id"))
                    .ifPresent(passengerRoleList::add);
        }
        return passengerRoleList;
    }
}
