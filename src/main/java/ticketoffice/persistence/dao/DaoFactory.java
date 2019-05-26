package ticketoffice.persistence.dao;

import ticketoffice.model.Passenger;
import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.impl.jdbc.PassengerDaoImpl;
import ticketoffice.persistence.dao.impl.jdbc.TicketDaoImpl;
import ticketoffice.persistence.dao.impl.jdbc.TrainDaoImpl;
import ticketoffice.persistence.dao.interfaces.PassengerDao;
import ticketoffice.persistence.dao.interfaces.TicketDao;
import ticketoffice.persistence.dao.interfaces.TrainDao;

public class DaoFactory {

    private DaoFactory() {
    }

    private static class SingletonDaoFactory {
        private final static DaoFactory daoFactory = new DaoFactory();
    }

    public static DaoFactory getInstance(){
        return SingletonDaoFactory.daoFactory;
    }

    public TrainDao getTrainDao(){
        return new TrainDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public TicketDao getTicketDao(){
        return new TicketDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public PassengerDao getPassengerDao(){
        return new PassengerDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }
}
