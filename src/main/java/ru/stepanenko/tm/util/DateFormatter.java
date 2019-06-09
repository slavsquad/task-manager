package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm";

    public static String dateToString(@Nullable final Date date) {

        if (date == null) return null;
        return dateToInput(date).replace('T', ' ');
    }

    public static String dateToInput(@Nullable final Date date) {

        if (date == null) return null;
        @NotNull final DateFormat df = new SimpleDateFormat(PATTERN);
        return df.format(date);
    }

    public static Date stringToDate(@Nullable final String string) throws ParseException {

        if (string == null || string.isEmpty()) return null;
        return new SimpleDateFormat(PATTERN).parse(string);
    }
}
