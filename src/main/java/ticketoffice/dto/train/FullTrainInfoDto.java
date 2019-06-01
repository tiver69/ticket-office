package ticketoffice.dto.train;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ticketoffice.dto.coach.TrainCoachPlacesInfoDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FullTrainInfoDto extends TrainInfoDto {
    private List<TrainCoachPlacesInfoDto> trainCoachPlacesInfoDtoList;
}
