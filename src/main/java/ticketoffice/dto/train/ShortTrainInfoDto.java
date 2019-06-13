package ticketoffice.dto.train;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.dto.coach.CoachTypePlacesInfoDto;

import java.util.List;

/**
 * Child entity for keeping additional information (for /user/booking page)
 * about amount of places in each coach type
 */
@Getter
@Setter
@NoArgsConstructor
public class ShortTrainInfoDto extends TrainInfoDto {
    private List<CoachTypePlacesInfoDto> coachTypePlacesInfoDtoList;
}
