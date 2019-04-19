package ru.stepanenko.tm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String format(final Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd.MM.yyyy");
        return formatter.format(date);
    }
}
