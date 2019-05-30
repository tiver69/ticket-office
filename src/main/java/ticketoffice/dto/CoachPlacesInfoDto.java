package ticketoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.CoachType;

@Getter
@Setter
@NoArgsConstructor
public class CoachPlacesInfoDto {

    private CoachType coachType;
    private int quantity;
    private int totalPlaces;
    private int availablePlaces;
}
