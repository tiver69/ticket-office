package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.CoachType;

/**
 * Interface for sql-queries in CoachType table.
 */
public interface CoachTypeDao extends AbstractDao<CoachType> {
    /**
     * Set locale variable for extracting specific language column
     *
     * @param locale - string value of language, can be "ru" or "en"
     */
    void setLocale(String locale);
}
