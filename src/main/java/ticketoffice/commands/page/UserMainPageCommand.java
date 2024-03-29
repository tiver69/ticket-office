package ticketoffice.commands.page;

import ticketoffice.commands.Command;
import ticketoffice.dto.TicketInfoDto;
import ticketoffice.dto.UserDto;
import ticketoffice.facade.TicketInfoFacade;
import ticketoffice.model.Station;
import ticketoffice.model.Ticket;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.StationDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.service.TicketService;
import ticketoffice.service.utils.TimeDateUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Set request attribute "pages" with total number of pages available.
 * Set request attribute "currentPage" with current number of page from request parameter.
 * Set request attribute "stations" with list of all stations for dropdown check item at page.
 * Set request attribute "displayActive" with true/false value according
 * to filtering ticket date(active/history).
 * Get list of tickets (from current page*5 to currentPage*5+5) item of current user from session
 * and set result list to request attribute "ticketList".
 * Forward to user/home page if there was no errors.
 */
public class UserMainPageCommand implements Command {

    TicketService ticketService = new TicketService();
    TicketInfoFacade ticketInfoFacade = new TicketInfoFacade();

    @Override
    public String execute(HttpServletRequest request) {

        String locale = (String) request.getSession().getAttribute("locale");
        List<Station> stationList;
        try (StationDao stationDao = DaoFactory.getInstance().getStationDao()) {
            stationDao.setLocale(locale);
            stationList = stationDao.getAll();
        }

        UserDto user = (UserDto) request.getSession().getAttribute("user");
        boolean displayActive = request.getParameter("displayType") == null ?
                true : Integer.parseInt(request.getParameter("displayType")) == 0;
        int currentPage = (int) request.getAttribute("currentPage") - 1;

        List<TicketInfoDto> ticketInfoDtoList = new ArrayList<>();
        try (TicketDao ticketDao = DaoFactory.getInstance().getTicketDao()) {
            List<Ticket> ticketList =
                    ticketDao.getTicketsByPassengerId(user.getPassenger().getId()).stream()
                            .filter(ticket ->
                                    (ticket.getDate().compareTo(TimeDateUtil.getTodayInFormat()) >= 0)
                                            == displayActive).collect(Collectors.toCollection(ArrayList::new));
            request.setAttribute("pages",
                    (int) Math.ceil(((double) ticketList.size()) / 5));
            ticketList.stream()
                    .sorted(Comparator.comparingLong(ticket -> ticket.getDate().getTime()))
                    .skip(currentPage * 5)
                    .limit(5)
                    .forEach(ticket -> {
                        ticketService.fillTicket(ticket, locale);
                        ticketInfoDtoList.add(ticketInfoFacade.loadTicketInfo(ticket));
                    });
        }

        request.setAttribute("displayType", displayActive ? 0 : 1);
        request.setAttribute("ticketList", ticketInfoDtoList);
        request.setAttribute("stations", stationList);
        return "user/home";
    }
}
