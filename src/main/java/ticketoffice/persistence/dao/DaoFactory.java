package ticketoffice.persistence.dao;

import ticketoffice.persistence.ConnectionPool;
import ticketoffice.persistence.dao.impl.jdbc.*;
import ticketoffice.persistence.dao.interfaces.*;

/**
 * Singleton factory class for creating Model-Dao items and setting into connection from Pool
 */
public class DaoFactory {

    private DaoFactory() {
    }

    private static class SingletonDaoFactory {
        private final static DaoFactory daoFactory = new DaoFactory();
    }

    public static DaoFactory getInstance() {
        return SingletonDaoFactory.daoFactory;
    }

    public TrainDao getTrainDao() {
        return new TrainDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public TicketDao getTicketDao() {
        return new TicketDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public PassengerDao getPassengerDao() {
        return new PassengerDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public PassengerRoleDao getPassengerRoleDao() {
        return new PassengerRoleDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public StationDao getStationDao() {
        return new StationDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public TrainStationDao getTrainStationDao() {
        return new TrainStationDaoImpl(
                ConnectionPool.getInstance().getConnection());
    }

    public TrainCoachDao getTrainCoachDao() {
        return new TrainCoachDaoImpl(
                ConnectionPool.getInstance().getConnection()
        );
    }

    public CoachTypeDao getCoachTypeDao() {
        return new CoachTypeDaoImpl(
                ConnectionPool.getInstance().getConnection()
        );
    }
}
