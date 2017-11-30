package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by makri on 17/05/2017.
 */
public class DateHelper {
    // TODO: Do I need a datehelper class at some point???
    public static String getDateAsString(Calendar cal) {
        String month, day;
        int year = cal.get(Calendar.YEAR);


        // Add leading zeros to month & day
        if ((cal.get(Calendar.MONTH) + 1) < 10) {
            month = "0" + Integer.toString(cal.get(Calendar.MONTH) + 1);
        } else {
            month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        }

        if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH) + 1);
        } else {
            day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        }

        String date = Integer.toString(year) + month + day;

        return date;
    }

    public static Calendar getCurrentDate() {
        Calendar cal = new GregorianCalendar();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today;
    }

    public static Date getDateFromString(String input) throws Exception{

        Date date = null;
        if(null == input)
        {
            return null;
        }
        if (input.toLowerCase().contains("now") || input.toLowerCase().contains("today")) {
            date = new Date();
        } else if (input.toLowerCase().contains("+")) {
            int daysAdd = Integer.parseInt(input.toLowerCase().replace("+", ""));
            Calendar c = Calendar.getInstance();

            c.setTime(new Date());

            c.add(Calendar.DATE, daysAdd);

            date = c.getTime();
        } else if (input.toLowerCase().contains("-")) {
            int daysAdd = 0-Integer.parseInt(input.toLowerCase().replace("-", ""));
            Calendar c = Calendar.getInstance();

            c.setTime(new Date());

            c.add(Calendar.DATE, daysAdd);

            date = c.getTime();
        } else {
            //give a legit date format input
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            date = df.parse(input);
        }
        return date;
    }

    public static String DateFormatConvert(String date, String oldPattern, String newPattern) throws ParseException {

        SimpleDateFormat simpleDateOldFormat = new SimpleDateFormat(oldPattern);
        return DateFormatConvert(simpleDateOldFormat.parse(date),newPattern);
    }

    public static String DateFormatConvert(Date date, String newPattern) throws ParseException {
        if(null == date)
        {
            return null;
        }
        SimpleDateFormat simpleDateNewFormat = new SimpleDateFormat(newPattern);
        return simpleDateNewFormat.format(date);
    }

    public static String getCharForNumber(int i) {
        return i >= 0 && i < 27 ? String.valueOf((char) (i + 65)) : null;
    }

    public static void collectDigits(int num, List<Integer> digits) {
        if (num / 10 > 0) {
            collectDigits(num / 10, digits);
        }
        digits.add(num % 10);
    }

    public static String covertNumberToAlphatletter(Integer number) {
        List<Integer> digits = new ArrayList<Integer>();
        collectDigits(number, digits);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits.size(); i++) {
            stringBuilder.append(getCharForNumber(digits.get(i)));
        }

        return stringBuilder.toString();
    }
}
