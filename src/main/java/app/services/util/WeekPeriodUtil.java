package app.services.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Date;
import java.util.Objects;

public class WeekPeriodUtil {

    private Date startWeek;
    private Date endWeek;

    public WeekPeriodUtil() {
    }

    public WeekPeriodUtil(Date date) {
        checkDate(date);
        DateTime dateTime = new DateTime(date);
        this.startWeek = dateTime.withDayOfWeek(DateTimeConstants.MONDAY)
                .withTimeAtStartOfDay().toDate();
        this.endWeek = dateTime.withDayOfWeek(DateTimeConstants.SUNDAY)
                .withTimeAtStartOfDay().toDate();
    }

    public WeekPeriodUtil(Date startWeek, Date endWeek) {
        checkDate(startWeek);
        checkDate(endWeek);
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public Date getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Date startWeek) {
        checkDate(startWeek);
        this.startWeek = startWeek;
    }

    public Date getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Date endWeek) {
        checkDate(endWeek);
        this.endWeek = endWeek;
    }

    private void checkDate(Date date) {
        if (date == null || date.getTime() <= 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WeekPeriodUtil)) {
            return false;
        }
        WeekPeriodUtil that = (WeekPeriodUtil) object;
        return Objects.equals(getStartWeek(), that.getStartWeek())
                && Objects.equals(getEndWeek(), that.getEndWeek());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartWeek(), getEndWeek());
    }
}