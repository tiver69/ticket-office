package ticketoffice.persistence.mapper;

import ticketoffice.model.CoachType;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.dao.interfaces.CoachTypeDao;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoachTypeMapper  implements Mapper<CoachType> {

    private CoachTypeDao coachTypeDao;

    public void setDao(AbstractDao passengerDao) {
        this.coachTypeDao = (CoachTypeDao) passengerDao;
    }

    @Override
    public CoachType extractItem(ResultSet resultSet) throws SQLException {
        CoachType coachType = new CoachType();
        while (resultSet.next()) {
            coachType.setId(resultSet.getInt("id"));
            coachType.setName(resultSet.getString("name"));
            coachType.setPlaces(resultSet.getInt("places"));
            coachType.setMarkup(resultSet.getInt("markup"));
        }
        return coachType;
    }

    @Override
    public ArrayList<CoachType> extractItemList(ResultSet resultSet) throws SQLException {
        ArrayList<CoachType> coachTypeList = new ArrayList<>();
        while (resultSet.next()) {
            coachTypeDao.getById(resultSet.getInt("id"))
                    .map(coachTypeList::add);
        }
        return coachTypeList;
    }
}
