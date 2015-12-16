/**
 *
 */
package com.airline.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 */
public class DateUtil {

    private static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * Transform ISO 8601 string to Calendar.
     */
    public static Date toDate(final String iso8601string)
            throws ParseException {
        //nowAsISO = "2013-05-31T00:00:00Z";
        Date finalResult = df1.parse(iso8601string);
        return finalResult;
    }

    public static String formatDate(Date date) {
        return df1.format(date);
    }
}
