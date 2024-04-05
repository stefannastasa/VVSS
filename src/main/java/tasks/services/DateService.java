package tasks.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateService {
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_A_DAY = 24;

    public static LocalDate getLocalDateValueFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public Date getDateValueFromLocalDate(LocalDate localDate) {
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public Date getDateMergedWithTime(String time, Date noTimeDate) {
        if (time == null || noTimeDate == null) {
            throw new IllegalArgumentException("Time or date cannot be null");
        }

        Date epoch = new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime();
        if (noTimeDate.before(epoch)) {
            throw new IllegalArgumentException("Date must be equal or after 01.01.1970");
        }

        String[] units = time.split(":");
        if (units.length != 2) {
            throw new IllegalArgumentException("Invalid time format");
        }

        int hour, minute;
        try {
            hour = Integer.parseInt(units[0]);
            minute = Integer.parseInt(units[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hour and minute must be integers");
        }

        if (hour < 0 || hour >= HOURS_IN_A_DAY) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }

        if (minute < 0 || minute >= MINUTES_IN_HOUR) {
            throw new IllegalArgumentException("Minute must be between 0 and 59");
        }

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(noTimeDate);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public String fromTimeUnit(int timeUnit) {
        StringBuilder sb = new StringBuilder();
        if (timeUnit < 10) sb.append("0");
        sb.append(timeUnit);
        return sb.toString();
    }

    public String getTimeOfTheDayFromDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return fromTimeUnit(hours) + ":" + fromTimeUnit(minutes);
    }
}
