package ticketoffice.facade;

import org.apache.log4j.Logger;
import ticketoffice.dto.UserDto;
import ticketoffice.model.Passenger;
import ticketoffice.service.PassengerRoleService;
import ticketoffice.service.PassengerService;

import java.util.Optional;

/**
 * Service class for creating UserDto objects
 */
public class UserFacade {

    private static Logger LOG = Logger.getLogger(UserFacade.class);
    private PassengerService passengerService = new PassengerService();
    private PassengerRoleService passengerRoleService = new PassengerRoleService();

    /**
     * Load requested passenger and  its information form bd, after verifying
     * correctness of password
     *
     * @param login    -   sting parameter with requested user login
     * @param password -   sting parameter with requested user password
     * @return Optional with UserDto value if password was correct, optional of Nullable
     * if password/login was incorrect
     */
    public Optional<UserDto> loadUser(String login, String password) {
        UserDto user = null;
        Optional<Passenger> passenger;
        if ((passenger = passengerService.loginPassenger(login, password))
                .isPresent()) {
            user = new UserDto();
            user.setPassenger(passenger.get());
            user.setRoles(passengerRoleService.getRolesByPassengerId(passenger.get().getId()));
            LOG.info(String.format("Successful authorization from %s user", login));
        } else {
            LOG.error(String.format("Error while authorization for %s user", login));
        }
        return Optional.ofNullable(user);
    }
}
