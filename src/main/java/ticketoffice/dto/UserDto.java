package ticketoffice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ticketoffice.model.Passenger;
import ticketoffice.model.enums.Role;

import java.util.List;

/**
 * Entity for keeping information about passenger and his roles
 */
@Setter
@Getter
@ToString
public class UserDto {
    private Passenger passenger;
    private List<Role> roles;

    public UserDto() {
    }

    public UserDto(Passenger passenger, List<Role> roles) {
        this.passenger = passenger;
        this.roles = roles;
    }
}
