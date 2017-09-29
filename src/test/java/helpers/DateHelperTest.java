package helpers;

import org.junit.Assert;
import org.junit.Test;
import sun.util.calendar.Gregorian;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by makri on 14/06/2017.
 */
public class DateHelperTest {
    @Test
    public void getDateAsString() throws Exception {

        DateHelper.getDateAsString(DateHelper.getCurrentDate());

        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String DateFromDate = format1.format(new Date());

        Assert.assertEquals(DateHelper.getDateAsString(DateHelper.getCurrentDate()), DateFromDate);
    }

    @Test
    public void getCurrentDate() throws Exception {
        Calendar calendar = DateHelper.getCurrentDate();
        Date date1 = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String dateFromgetCurrentDate = format1.format(date1);

        String DateFromDate = format1.format(new Date());

        Assert.assertEquals(dateFromgetCurrentDate, DateFromDate);


    }

    @Test
    public void getDateFromString1() throws Exception {

        DateHelper.getCurrentDate().toString();
        Assert.assertTrue(DateHelper.getDateFromString("+200").before(DateHelper.getDateFromString("+201")));
    }

    @Test
    public void getDateFromString2() throws Exception {

        Assert.assertEquals(DateHelper.getDateFromString("today").toString() + "= " + DateHelper.getDateFromString("now").toString(), DateHelper.getDateFromString("+0"), DateHelper.getDateFromString("now"));


    }

    @Test(expected = java.text.ParseException.class)
    public void getDateFromString3() throws Exception {
        Assert.assertNull(DateHelper.getDateFromString("-1"));


    }
}
