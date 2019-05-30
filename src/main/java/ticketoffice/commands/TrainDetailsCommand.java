package ticketoffice.commands;

import ticketoffice.facade.TrainInfoFacade;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class TrainDetailsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        int selectedTrainId = Integer.parseInt(request.getParameter("selectedTrainId"));

        request.setAttribute("selectedTrainId",
                selectedTrainId);
        return "user/booking/train";
    }

}