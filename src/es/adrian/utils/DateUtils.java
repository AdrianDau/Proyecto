package es.adrian.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils
{

    private static DateTimeFormatter simple_day_month_formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static DateTimeFormatter day_and_hour_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    public static String createNewDate() {
        String pattern = "MM-dd-yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());

    }

    public static Date fromLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate fromDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate fromStringToLocalDate(String date) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, simple_day_month_formatter);
            System.out.println(localDate);
        } catch(Exception e) {
        }

        return localDate;
    }

}
