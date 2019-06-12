package ticketoffice.persistence.dao.interfaces;

import ticketoffice.model.Station;

/**
 * Interface for sql-queries in Station table.
 */
public interface StationDao extends AbstractDao<Station> {
    /**
     * Set locale variable for extracting specific language column
     *
     * @param locale - string value of language, can be "ru" or "en"
     */
    void setLocale(String locale);
}
