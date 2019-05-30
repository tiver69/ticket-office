package ticketoffice.commands;

import ticketoffice.facade.TrainInfoFacade;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class FindTrainCommand implements Command {

    private TrainInfoFacade trainInfoFacade = new TrainInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {
        int departureStation = Integer.parseInt(request.getParameter("departureStation"));
        int destinationStation = Integer.parseInt(request.getParameter("destinationStation"));
        Date date = Date.valueOf(request.getParameter("departureDate"));

        request.setAttribute("trainsInformation",
                trainInfoFacade.getRequestTrainInformation(departureStation, destinationStation,date));
        return "user/booking";
    }

}