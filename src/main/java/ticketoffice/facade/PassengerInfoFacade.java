package ticketoffice.facade;

import org.apache.log4j.Logger;
import ticketoffice.dto.PassengerInfoDto;
import ticketoffice.model.Passenger;
import ticketoffice.model.PassengerRole;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerRoleDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PassengerInfoFacade {

    private static Logger LOG = Logger.getLogger(TicketInfoFacade.class);

    public PassengerInfoDto loadPassengerInfo(Passenger passenger){

        PassengerInfoDto passengerInfoDto = new PassengerInfoDto();
        passengerInfoDto.setPassenger(passenger);

        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            List<Ticket> passengerTicketList = ticketDao.getTicketsByPassengerId(passenger.getId());
            passengerInfoDto.setTotalTicket
                    (passengerTicketList.size());
            if (!passengerTicketList.isEmpty()) {
                passengerTicketList = passengerTicketList.stream().sorted(
                        Comparator.comparingLong(ticket -> ticket.getDate().getTime())).collect(Collectors.toList());
                passengerInfoDto.setLastActive(passengerTicketList.get(passengerTicketList.size()-1).getDate());
            }
        }

        try (PassengerRoleDao passengerRoleDao = DaoFactory.getInstance().getPassengerRoleDao()){
            List<String> roles = new ArrayList<>();
            passengerRoleDao.getPassengerRolesByPassengerId(passenger.getId())
                    .forEach(passengerRole -> roles.add(passengerRole.getRole().toString()));
            passengerInfoDto.setRoles(roles);
        }

        return passengerInfoDto;
    }
}
