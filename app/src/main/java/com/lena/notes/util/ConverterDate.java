package com.lena.notes.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConverterDate {

    private static String dateFormat = "dd.MM.yy";
    private static String timeFormat = "HH:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat, Locale.getDefault());

    public static String getDate(Long ms) {
        Date date = new Date(ms);
        return simpleDateFormat.format(date);
    }

    public static String getTime(Long ms) {
        Date date = new Date(ms);
        return simpleTimeFormat.format(date);
    }
}