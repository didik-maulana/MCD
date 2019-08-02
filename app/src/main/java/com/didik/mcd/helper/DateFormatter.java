package com.didik.mcd.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatter {

    private static final String FORMAT_DATE = "yyyy-MM-dd";
    private static final String NEW_FORMAT_DATE = "dd MMMM yyyy";

    public String formatDate(String date) {
        String formattedDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date parseDate = sdf.parse(date);
            formattedDate = new SimpleDateFormat(NEW_FORMAT_DATE, Locale.getDefault()).format(parseDate);
        } catch (ParseException error) {
            error.printStackTrace();
        }
        return formattedDate;
    }
}
