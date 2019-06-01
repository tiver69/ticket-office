package ticketoffice.service.utils;

import org.apache.log4j.Logger;
import ticketoffice.facade.train.ShortTrainInfoFacade;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeDateUtil {

    private static Logger LOG = Logger.getLogger(ShortTrainInfoFacade.class);

    private static String YEAR_FORMAT = "YYYY";
    private static String FIRST_JANUARY = "-01-01";
    private static String TIME_FORMAT = "HH:mm:ss";
    private static String GRINVICH_TIME_ZONE = "UTC";

    public static int getDayOfYearOfDate(Date requestDate){
        String firstDayOfOrderYear = new SimpleDateFormat(YEAR_FORMAT)
                .format(requestDate)+FIRST_JANUARY;
        Date firstDayOfOrderYearDate = Date.valueOf(firstDayOfOrderYear);
        long diffDays = requestDate.getTime() - firstDayOfOrderYearDate.getTime();

        return (int) TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS) + 1;
    }

    public static Time getTimeDiff(Time lesserTime, Time biggerTime){
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        timeFormat.setTimeZone(TimeZone.getTimeZone(GRINVICH_TIME_ZONE));
        Time difference = new Time(biggerTime.getTime()-lesserTime.getTime());

        return Time.valueOf(timeFormat.format(difference));
    }
}
