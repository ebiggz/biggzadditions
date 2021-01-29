package net.ebiggz.biggzadditions.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static long timeInMillis() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public static String dateAndTimeFromMills(Long milliseconds) {
        Date dateFromMills = new Date(milliseconds);
        DateFormat df = new SimpleDateFormat("M/d/yy h:mm a");
        String date = df.format(dateFromMills);
        return date;
    }
}
