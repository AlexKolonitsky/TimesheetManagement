package app.entities.namespace;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Date;

public enum LogsNamespace {

    TODAY(DateTime.now().withTimeAtStartOfDay().toDate()),
    THIS_WEEK(DateTime.now()
            .withDayOfWeek(DateTimeConstants.MONDAY)
            .withTimeAtStartOfDay().toDate()),
    THIS_MONTH(DateTime.now()
            .withDayOfMonth(Constants.NUMBER_FIRST_DAY_IN_MONTH)
            .withTimeAtStartOfDay().toDate());

    private Date boundaryDate;

    LogsNamespace(Date boundaryDate) {
        this.boundaryDate = boundaryDate;
    }

    public Date getBoundaryDate() {
        return boundaryDate;
    }

    private static class Constants {
        private static final int NUMBER_FIRST_DAY_IN_MONTH = 1;
    }
}
