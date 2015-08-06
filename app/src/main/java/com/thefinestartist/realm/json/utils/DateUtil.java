package com.thefinestartist.realm.json.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TheFinestArtist on 8/6/15.
 */
public class DateUtil {

    public static String DATE_FORMAT = "MMM d, yyyy h:mm:ss aa"; // Aug 6, 2015 1:23:33 AM
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

    private DateUtil() {
    }

    public static String getDateFormat(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }
}
