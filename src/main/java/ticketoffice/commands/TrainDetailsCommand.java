package ticketoffice.commands;

import org.apache.log4j.Logger;
import ticketoffice.dto.train.FullTrainInfoDto;
import ticketoffice.exceptions.ValidateFailException;
import ticketoffice.facade.train.FullTrainInfoFacade;
import ticketoffice.validator.DateValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.stream.Collectors;

public class TrainDetailsCommand implements Command {

    private static Logger LOG = Logger.getLogger(TrainDetailsCommand.class);
    private FullTrainInfoFacade fullTrainInfoFacade = new FullTrainInfoFacade();
    private DateValidator dateValidator = new DateValidator();

    private int selectedTrainId;
    private int departureStation;
    private int destinationStation;
    private int currentPage;
    private Date date;

    @Override
    public String execute(HttpServletRequest request) {

        String locale = (String) request.getSession().getAttribute("locale");
        FullTrainInfoDto fullTrainInfoDto;
        try {
            selectedTrainId = Integer.parseInt(request.getParameter("selectedTrainId"));
            departureStation = Integer.parseInt(request.getParameter("departureStationHidden"));
            destinationStation = Integer.parseInt(request.getParameter("destinationStationHidden"));
            currentPage = (int) request.getAttribute("currentPage");
            date = Date.valueOf(request.getParameter("departureDateHidden"));

            validateRequest();
            fullTrainInfoDto =
                    fullTrainInfoFacade.getRequestTrainInformation(
                            departureStation, destinationStation, date, selectedTrainId, locale);
        } catch (ValidateFailException e) {
            request.setAttribute("errorCode", e.getErrorKeyMessage());
            return ("error/400");
        } catch (IllegalArgumentException e) {
            LOG.error("Illegal arguments in search parameters for /trainDetail request");
            return ("error/400");
        }

        LOG.info(String.format("Detail information request for train #%d on %s",
                selectedTrainId, date.toString()));

        request.setAttribute("pages",
                (int) Math.ceil(fullTrainInfoDto.getTrainCoachPlacesInfoDtoList().size()));
        if (currentPage > fullTrainInfoDto.getTrainCoachPlacesInfoDtoList().size()) {
            currentPage = fullTrainInfoDto.getTrainCoachPlacesInfoDtoList().size();
            request.setAttribute("currentPage", currentPage);
        }

        fullTrainInfoDto.setTrainCoachPlacesInfoDtoList(
                fullTrainInfoDto.getTrainCoachPlacesInfoDtoList().stream()
                        .filter((trainCoachPlacesInfoDto) ->
                                trainCoachPlacesInfoDto.getTrainCoach().getNumber() == currentPage
                        ).collect(Collectors.toList()));

        request.setAttribute("trainInformation", fullTrainInfoDto);
        return "user/booking/train";
    }

    private void validateRequest() throws ValidateFailException {
        dateValidator.validateDate(date);
    }
}
