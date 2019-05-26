package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Passenger {

    private int id;
    private String lastName;
    private String firstName;

    public Passenger() {
    }

    public Passenger(int id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Passenger)) return false;
        Passenger otherPassenger = (Passenger) obj;
        return (id == otherPassenger.getId())
                && firstName.equals(otherPassenger.getFirstName())
                && lastName.equals(otherPassenger.getLastName());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }
}
