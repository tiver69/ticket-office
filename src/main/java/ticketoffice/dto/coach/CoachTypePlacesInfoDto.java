package ticketoffice.dto.coach;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.model.CoachType;

/**
 * Entity for keeping information about booked places in requested coach type:
 *  coach type, amount of this type coaches in train,
 *  amount of places for booking in this coach type,
 *  amount of not booked places in this coach type
 */
@Getter
@Setter
@NoArgsConstructor
public class CoachTypePlacesInfoDto {

    private CoachType coachType;
    private int quantity;
    private int totalPlaces;
    private int availablePlaces;
}
