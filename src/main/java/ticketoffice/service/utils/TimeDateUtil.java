package ticketoffice.service.utils;

import org.apache.log4j.Logger;
import ticketoffice.facade.train.ShortTrainInfoFacade;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeDateUtil {

    public static long ONE_DAY = 24 * 60 * 60 * 1000;
    private static String DAY_NUM_IN_YEAR = "DD";
    private static String TIME_FORMAT = "HH:mm:ss";
    private static String GRINVICH_TIME_ZONE = "UTC";
    private static String KYIV_TIME_ZONE = "Etc/GMT-4";

    public static int getDayOfYearOfDate(Date requestDate){
        return Integer.parseInt(
                new SimpleDateFormat(DAY_NUM_IN_YEAR).format(requestDate));
    }

    public static Time getTimeDiff(Time lesserTime, Time biggerTime){
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        timeFormat.setTimeZone(TimeZone.getTimeZone(GRINVICH_TIME_ZONE));
        Time difference = new Time(biggerTime.getTime()-lesserTime.getTime());

        return Time.valueOf(timeFormat.format(difference));
    }

    public static Time convertToLocalTime(Time time){
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        timeFormat.setTimeZone(TimeZone.getTimeZone(KYIV_TIME_ZONE));
        return Time.valueOf(timeFormat.format(time));
    }

    public static Date getThreeMonthLater(){
        Date today = new Date(System.currentTimeMillis());
        return new Date(today.getTime() + ONE_DAY * 90);
    }
}
