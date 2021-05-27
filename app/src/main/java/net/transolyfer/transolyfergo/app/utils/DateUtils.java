package net.transolyfer.transolyfergo.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by garymontengro on 23/03/17.
 */

public class DateUtils {

    public static String getActualDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getActualDateAndHour(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        String formattedDate = df.format(c.getTime()).replace(" ", "_");
        return formattedDate;
    }
}
