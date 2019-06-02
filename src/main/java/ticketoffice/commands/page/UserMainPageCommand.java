package ticketoffice.commands.page;

import ticketoffice.commands.Command;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.dto.UserDto;
import ticketoffice.facade.TicketInfoFacade;
import ticketoffice.model.Station;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UserMainPageCommand implements Command {

    TicketService ticketService = new TicketService();
    TicketInfoFacade ticketInfoFacade = new TicketInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {

        List<Station> stationList;
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationList = stationDao.getAll();
        }

        boolean displayActive = request.getParameter("displayType") == null ?
                true : Integer.parseInt(request.getParameter("displayType")) == 0;

        UserDto user = (UserDto) request.getSession().getAttribute("user");
        List<TicketInfoDto> ticketInfoDtoList = new ArrayList<>();
        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            ticketDao.getTicketsByPassengerId(user.getPassenger().getId()).stream()
                    .filter(ticket ->
                            (ticket.getDate().compareTo(new Date(System.currentTimeMillis())) > 0)
                                    == displayActive)
                    .sorted(Comparator.comparingLong(ticket -> ticket.getDate().getTime()))
                    .forEach(ticket -> {
                        ticketService.fillTicket(ticket);
                        ticketInfoDtoList.add(ticketInfoFacade.loadTicketInfo(ticket));
                    });
        }

        request.setAttribute("displayType", displayActive?0:1);
        request.setAttribute("ticketList", ticketInfoDtoList);
        request.setAttribute("stations", stationList);
        return "user/home";
    }
}