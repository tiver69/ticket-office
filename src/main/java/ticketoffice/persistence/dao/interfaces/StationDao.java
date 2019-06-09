package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Station;

public interface StationDao extends AbstractDao<Station>{
    void setLocale(String locale);
}
