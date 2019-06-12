package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Ticket;

import java.sql.Timestamp;

/**
 * Entity for keeping information about ticket, information about
 * arriving and departure date-time.
 */
@Getter
@Setter
@NoArgsConstructor
public class TicketInfoDto {
    private Ticket ticket;
    private Timestamp departureDateTime;
    private Timestamp arrivalDateTime;
}
