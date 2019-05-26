package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Train {
    private int id;
    private int frequency;
    private int markup;

    public Train() {
    }

    public Train(int id, int frequency, int markup) {
        this.id = id;
        this.frequency = frequency;
        this.markup = markup;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Train)) return false;
        Train otherTrain = (Train)obj;
        return id == otherTrain.getId()
                && (frequency == otherTrain.getFrequency())
                && (markup == otherTrain.getMarkup());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + frequency;
        result = 31 * result + markup;
        return result;
    }
}
