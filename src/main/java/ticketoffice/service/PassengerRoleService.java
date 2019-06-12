package ticketoffice.service;

import org.apache.log4j.Logger;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.enums.Role;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;

import java.util.ArrayList;
import java.util.List;

public class PassengerRoleService {

    private static Logger LOG = Logger.getLogger(PassengerRoleService.class);

    /**
     * @param passengerId
     * @return extracted Role from Optional value.
     * @throws ValidateFailException in case no item was not found.
     */
    public List<Role> getRolesByPassengerId(int passengerId) {
        List<Role> roleList = new ArrayList<>();
        try (PassengerRoleDao passengerRoleDao = DaoFactory.getInstance().getPassengerRoleDao()) {
            List<PassengerRole> passengerRoleList
                    = passengerRoleDao.getPassengerRolesByPassengerId(passengerId);
            passengerRoleList.forEach(passengerRole -> {
                roleList.add(passengerRole.getRole());
            });
        }
        LOG.info(String.format("Load %s role(s) for user#%d", roleList.toString(), passengerId));
        return roleList;
    }
}
