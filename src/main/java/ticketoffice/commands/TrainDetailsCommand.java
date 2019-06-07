package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.dto.train.FullTrainInfoDto;
import ticketoffice.facade.train.FullTrainInfoFacade;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.stream.Collectors;

public class TrainDetailsCommand implements Command {

    private static Logger LOG = Logger.getLogger(TrainDetailsCommand.class);
    private FullTrainInfoFacade fullTrainInfoFacade = new FullTrainInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {
        int selectedTrainId = Integer.parseInt(request.getParameter("selectedTrainId"));
        int departureStation = Integer.parseInt(request.getParameter("departureStationHidden"));
        int destinationStation = Integer.parseInt(request.getParameter("destinationStationHidden"));
        int currentPage = (int) request.getAttribute("currentPage");
        Date date = Date.valueOf(request.getParameter("departureDateHidden"));

        LOG.info(String.format("Detail information request for train #%d on %s",
                selectedTrainId, date.toString()));

        FullTrainInfoDto fullTrainInfoDto =
                fullTrainInfoFacade.getRequestTrainInformation(departureStation, destinationStation, date, selectedTrainId);
        request.setAttribute("pages",
                (int) Math.ceil(fullTrainInfoDto.getTrainCoachPlacesInfoDtoList().size()));
        fullTrainInfoDto.setTrainCoachPlacesInfoDtoList(
                fullTrainInfoDto.getTrainCoachPlacesInfoDtoList().stream()
                        .filter((trainCoachPlacesInfoDto) ->
                                trainCoachPlacesInfoDto.getTrainCoach().getNumber() == currentPage
                        ).collect(Collectors.toList()));
        request.setAttribute("trainInformation", fullTrainInfoDto);
        return "user/booking/train";
    }

}