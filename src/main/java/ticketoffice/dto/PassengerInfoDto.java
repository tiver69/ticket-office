package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.Passenger;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PassengerInfoDto {

    private Passenger passenger;
    private int totalTicket;
    private Date lastActive;
    private List<String> roles;
}
