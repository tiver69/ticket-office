package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for keeping Passenger information, contains string variables of
 * last and first names, login and password(hash-value if entity was extracted from db)
 */
@Getter
@Setter
@ToString
public class Passenger {

    private int id;
    private String lastName;
    private String firstName;
    private String login;
    private String password;

    public Passenger() {
    }

    public Passenger(int id, String lastName, String firstName, String login, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Passenger)) return false;
        Passenger otherPassenger = (Passenger) obj;
        return (id == otherPassenger.getId())
                && firstName.equals(otherPassenger.getFirstName())
                && lastName.equals(otherPassenger.getLastName())
                && login.equals(otherPassenger.getLogin());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }
}
