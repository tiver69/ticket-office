package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ticketoffice.model.enums.Role;

/**
 * Entity for keeping PassengerRole information, contains Passenger variable and Role item
 */
@Getter
@Setter
@ToString
public class PassengerRole {
    private int id;
    private Passenger passenger;
    private Role role;

    public PassengerRole() {
    }

    public PassengerRole(int id, Passenger passenger, Role role) {
        this.id = id;
        this.passenger = passenger;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PassengerRole)) return false;
        PassengerRole otherPassengerRole = (PassengerRole) obj;
        return id == otherPassengerRole.getId()
                && passenger.getId() == otherPassengerRole.getPassenger().getId()
                && role == otherPassengerRole.role;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + passenger.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
