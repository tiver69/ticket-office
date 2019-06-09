package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.CoachType;

public interface CoachTypeDao extends AbstractDao<CoachType> {
    void setLocale(String locale);
}
