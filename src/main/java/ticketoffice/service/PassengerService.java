package ticketoffice.service;

import ticketoffice.model.Passenger;
import ticketoffice.persistence.dao.DaoFactory;
import ticketoffice.persistence.dao.interfaces.PassengerDao;

public class PassengerService {

    PassengerDao passengerDaoImpl;

    public PassengerService() {
        this.passengerDaoImpl = DaoFactory.getInstance().getPassengerDao();;
    }

    public boolean registerPassenger(Passenger passenger){
        return passengerDaoImpl.create(passenger) == 1;
    }
}
