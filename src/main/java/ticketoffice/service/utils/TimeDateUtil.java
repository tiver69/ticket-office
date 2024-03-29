package ticketoffice.service.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TimeDateUtil {

    public static long ONE_DAY = 24 * 60 * 60 * 1000;
    private static String DAY_NUM_IN_YEAR = "DD";
    private static String TIME_FORMAT = "HH:mm:ss";
    private static String DATE_FORMAT = "YYYY-MM-dd";
    private static String GRINVICH_TIME_ZONE = "UTC";
    private static String KYIV_TIME_ZONE = "Etc/GMT-4";

    /**
     * @param requestDate
     * @return -   int value, number of requested date from first day of year.
     */
    public static int getDayOfYearOfDate(Date requestDate) {
        return Integer.parseInt(
                new SimpleDateFormat(DAY_NUM_IN_YEAR).format(requestDate));
    }

    /**
     * @param lesserTime
     * @param biggerTime
     * @return -   Time value of difference between (biggerTime - lesserTime)
     */
    public static Time getTimeDiff(Time lesserTime, Time biggerTime) {
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        timeFormat.setTimeZone(TimeZone.getTimeZone(GRINVICH_TIME_ZONE));
        Time difference = new Time(biggerTime.getTime() - lesserTime.getTime());

        return Time.valueOf(timeFormat.format(difference));
    }

    /**
     * @param time
     * @return -   add KYIV time-zone difference (+3H) to time parameter in GRINVICH time-zone
     */
    public static Time convertToLocalTime(Time time) {
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        timeFormat.setTimeZone(TimeZone.getTimeZone(KYIV_TIME_ZONE));
        return Time.valueOf(timeFormat.format(time));
    }

    /**
     * @return Date of 180 days later from current date
     */
    public static Date getThreeMonthLater() {
        Date today = new Date(System.currentTimeMillis());
        return new Date(today.getTime() + ONE_DAY * 90);
    }

    /**
     * @return current date in yyyy-mm-dd format, with 00:00 time.
     */
    public static Date getTodayInFormat() {
        String today = new SimpleDateFormat(DATE_FORMAT)
                .format(new Date(System.currentTimeMillis()));
        return Date.valueOf(today);
    }
}
