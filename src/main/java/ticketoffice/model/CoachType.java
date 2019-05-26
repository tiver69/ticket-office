package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CoachType {

    private int id;
    private String name;
    private int places;
    private int markup;

    public CoachType() {
    }

    public CoachType(int id, String name, int places, int markup) {
        this.id = id;
        this.name = name;
        this.places = places;
        this.markup = markup;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CoachType)) return false;
        CoachType otherCoachType = (CoachType) obj;
        return (id == otherCoachType.getId())
                && name.equals(otherCoachType.getName())
                && (places == otherCoachType.getPlaces())
                && (markup == otherCoachType.getMarkup());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + places;
        return result;
    }
}
