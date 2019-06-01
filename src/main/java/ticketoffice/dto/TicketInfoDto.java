package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Ticket;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class TicketInfoDto {
    private Ticket ticket;
    private Timestamp departureDateTime;
    private Timestamp arrivalDateTime;
}
