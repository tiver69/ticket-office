package ticketoffice.dto.coach;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ticketoffice.model.Ticket;
import ticketoffice.model.TrainCoach;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrainCoachPlacesInfoDto {

    private TrainCoach trainCoach;
    private List<Integer> bookedPlaceList;
}
