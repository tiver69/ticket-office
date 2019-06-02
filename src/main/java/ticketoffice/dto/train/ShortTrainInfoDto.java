package ticketoffice.dto.train;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ticketoffice.dto.coach.CoachTypePlacesInfoDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShortTrainInfoDto extends TrainInfoDto {
    private List<CoachTypePlacesInfoDto> coachTypePlacesInfoDtoList;
}
