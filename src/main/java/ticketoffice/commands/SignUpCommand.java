package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.model.Passenger;
import ticketoffice.persistence.mapper.PassengerMapper;
import ticketoffice.service.PassengerService;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {

    private static Logger LOG = Logger.getLogger(SignUpCommand.class);
    private PassengerMapper passengerMapper = new PassengerMapper();
    private PassengerService passengerService = new PassengerService();

    @Override
    public String execute(HttpServletRequest request) {

        Passenger passenger = passengerMapper.extractItemFromRequest(request);
        LOG.info(String.format("Get sign up request: (%s, %s, %s)",
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getLogin()));

        if (passengerService.registerPassenger(passenger))
            return "redirect/login";
        else return "registration";
    }

}
