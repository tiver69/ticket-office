package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Passenger;

import java.sql.Date;
import java.util.List;

/**
 * Entity for keeping passenger information (for /admin/userlist page) and statistic:
 *  total amount of tickets for current passenger,
 *  date of latest ticket.
 */
@Getter
@Setter
@NoArgsConstructor
public class PassengerInfoDto {

    private Passenger passenger;
    private int totalTicket;
    private Date lastActive;
    private List<String> roles;
}
