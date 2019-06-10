package ticketoffice.service.utils;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;

import static org.hamcrest.CoreMatchers.equalTo;

public class TimeDateUtilTest {

    @Test
    public void getDayOfYearOfDateTest() {
        Date date = Date.valueOf("2019-06-10");
        int result = TimeDateUtil.getDayOfYearOfDate(date);
        Assert.assertThat(date.toString() + " is 161st day of year",
                result, equalTo(161));
    }

    @Test
    public void getTimeDiffTest() {
        Time biggerTime = Time.valueOf("00:51:51");
        Time lesserTime = Time.valueOf("00:31:51");
        Time result = TimeDateUtil.getTimeDiff(lesserTime, biggerTime);
        Assert.assertThat("Result time must be equals",
                result, equalTo(Time.valueOf("00:20:00")));
    }

    @Test
    public void convertToLocalTimeTest() {
        Time time = Time.valueOf("00:51:51");
        Time result = TimeDateUtil.convertToLocalTime(time);
        Assert.assertThat("Result time must be two hours later",
                result, equalTo(Time.valueOf("02:51:51")));
    }

    @Test
    public void getTodayInFormatTest(){
        Date today = new Date(System.currentTimeMillis());
        Date todayResult = TimeDateUtil.getTodayInFormat();
        Assert.assertThat("Result date must be equal",
                todayResult.toString(), equalTo(today.toString()));
    }
}
