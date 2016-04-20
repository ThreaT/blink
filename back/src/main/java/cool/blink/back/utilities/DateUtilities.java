package cool.blink.back.utilities;

import cool.blink.back.utilities.LogUtilities.Priority;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.datatype.XMLGregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtilities {

    public static final Integer ONE_SECOND_TO_MILLISECONDS = 1000;
    public static final Integer THREE_SECONDS_TO_MILLISECONDS = 3000;
    public static final Integer FIVE_SECONDS_TO_MILLISECONDS = 5000;
    public static final Integer TEN_SECONDS_TO_MILLISECONDS = 10000;
    public static final Integer ONE_MINUTE_TO_MILLISECONDS = 60000;
    public static final Integer FIVE_MINUTES_TO_MILLISECONDS = 300000;
    public static final Integer TEN_MINUTES_TO_MILLISECONDS = 600000;
    public static final Integer THIRTY_MINUTES_TO_MILLISECONDS = 1800000;
    public static final Integer ONE_HOUR_TO_MILLISECONDS = 3600000;
    public static final Integer SIX_HOURS_TO_MILLISECONDS = 21600000;
    public static final Integer TWELVE_HOURS_TO_MILLISECONDS = 43200000;
    public static final Integer ONE_DAY_TO_MILLISECONDS = 86400000;

    public static final DateTime convert(final String dateTime, final String inputFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(inputFormat);
        return dateTimeFormatter.parseDateTime(dateTime);
    }

    public static final String convert(final DateTime dateTime, final String outputFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(outputFormat);
        return dateTimeFormatter.print(dateTime);
    }

    public static final String convert(final java.sql.Date date, final String outputFormat) {
        DateFormat dateFormat = new SimpleDateFormat(outputFormat);
        return dateFormat.format(date);
    }

    public static final DateTime convert(final XMLGregorianCalendar xmlGregorianCalendar) {
        return new DateTime(xmlGregorianCalendar.toGregorianCalendar().getTime());
    }

    public static final DateTime convert(final Integer year, final Integer monthOfYear, final Integer dayOfMonth, final Integer hourOfDay, final Integer minuteOfHour, final Integer secondOfMinute) {
        return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute);
    }

    public static final DateTime convert(final java.sql.Date date) {
        return new DateTime(date.getTime());
    }

    public static final Timestamp convert(final DateTime dateTime) {
        return new Timestamp(dateTime.getMillis());
    }

    public static final DateTime convert(final Timestamp timestamp) {
        return new DateTime(timestamp);
    }

    public static final Boolean wasYesterday(final DateTime dateTime) {
        return dateTime.withTimeAtStartOfDay().isBefore(new DateTime().withTimeAtStartOfDay());
    }

    public static final Boolean hasElapsedHours(final DateTime dateTime, final Integer amount) {
        return dateTime.plusHours(amount).isBeforeNow();
    }

    public static final synchronized Long dateDifferenceInMillis(final Date before, final Date after) {
        return after.getTime() - before.getTime();
    }

    public static final synchronized Long dateDifferenceInMillis(final DateTime before, final DateTime after) {
        return after.getMillis() - before.getMillis();
    }

    @SuppressWarnings("SleepWhileHoldingLock")
    public static final synchronized DateTime generateUniqueDateTime() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(DateUtilities.class.getName()).log(Priority.HIGHEST, null, ex);
        }
        return new DateTime();
    }

}
