package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.PassengerRole;

import java.util.List;

public interface PassengerRoleDao extends AbstractDao<PassengerRole> {

    List<PassengerRole> getPassengerRolesByPassengerId(int passengerId);
}
