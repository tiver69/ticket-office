package ticketoffice.facade;

import ticketoffice.dto.UserDto;
import ticketoffice.model.Passenger;
import ticketoffice.service.PassengerRoleService;
import ticketoffice.service.PassengerService;

import java.util.Optional;

public class UserFacade {
    private PassengerService passengerService = new PassengerService();
    private PassengerRoleService passengerRoleService = new PassengerRoleService();

    public Optional<UserDto> loadUser(String login, String password){
        UserDto user = null;
        Optional<Passenger> passenger;
        if ((passenger = passengerService.loginPassenger(login, password))
                .isPresent()){
            user = new UserDto();
            user.setPassenger(passenger.get());
            user.setRoles(passengerRoleService.getRolesByPassengerId(passenger.get().getId()));
        }
        return Optional.ofNullable(user);
    }
}
