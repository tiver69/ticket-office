package ticketoffice.facade;

import ticketoffice.dto.UserDto;
import ticketoffice.model.Passenger;
import ticketoffice.service.PassengerRoleService;
import ticketoffice.service.PassengerService;

public class UserFacade {
    PassengerService passengerService = new PassengerService();
    PassengerRoleService passengerRoleService = new PassengerRoleService();

    public UserDto loadUser(String login, String password){
        UserDto user = null;
        Passenger passenger;
        if ((passenger = passengerService.loginPassenger(login, password))
                != null){
            user = new UserDto();
            user.setPassenger(passenger);
            user.setRoles(passengerRoleService.getRolesByPassengerId(passenger.getId()));
        }
        return user;
    }
}
