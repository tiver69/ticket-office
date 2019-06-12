package ticketoffice.dto.coach;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ticketoffice.model.TrainCoach;

import java.util.List;

/**
 * Entity for keeping information about booked places in requested train coach:
 * train coach and list of booked places numbers
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrainCoachPlacesInfoDto {

    private TrainCoach trainCoach;
    private List<Integer> bookedPlaceList;
}
