package ticketoffice.service;

import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import java.util.ArrayList;
import java.util.List;

public class PassengerRoleService {

    public List<Role> getRolesByPassengerId(int passengerId) {
        List<Role> roleList = new ArrayList<>();
        try (PassengerRoleDao passengerRoleDao = DaoFactory.getInstance().getPassengerRoleDao()) {
            List<PassengerRole> passengerRoleList
                    = passengerRoleDao.getPassengerRolesByPassengerId(passengerId);
            passengerRoleList.forEach(passengerRole -> {
                roleList.add(passengerRole.getRole());
            });
        }
        return roleList;
    }
}
