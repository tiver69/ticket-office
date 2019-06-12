package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for keeping Station information, contains station name
 * for displaying on web page
 */
@Getter
@Setter
@ToString
public class Station {

    private int id;
    private String name;

    public Station() {
    }

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Station)) return false;
        Station otherStation = (Station) obj;
        return id == otherStation.getId()
                && name.equals(otherStation.getName());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
