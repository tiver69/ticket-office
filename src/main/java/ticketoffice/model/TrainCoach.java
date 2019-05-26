package ticketoffice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrainCoach {

    private int id;
    private Train train;
    private CoachType coachType;
    private int number;

    public TrainCoach() {
    }

    public TrainCoach(int id, Train train, CoachType coachType, int number) {
        this.id = id;
        this.train = train;
        this.coachType = coachType;
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TrainCoach)) return false;
        TrainCoach otherTrainCoach = (TrainCoach)obj;
        return id == otherTrainCoach.getId()
                && train.equals(otherTrainCoach.getTrain())
                && coachType.equals(otherTrainCoach.getCoachType())
                && (number==otherTrainCoach.getNumber());
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + train.hashCode();
        result = 31 * result + coachType.hashCode();
        result = 31 * result + number;
        return result;
    }
}
