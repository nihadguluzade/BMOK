import java.time.LocalDate;
import java.util.Calendar;

/** Utility class */
public class Date {

    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /** Second constructor. Is used to clone object. */
    public Date(Date otherDate) {
        this.day = otherDate.day;
        this.month = otherDate.month;
        this.year = otherDate.year;
    }

    /** Adds month(s) to the date. */
    public Date sumMonths(int duration) {
        month += + duration;
        if (month > 12) {
            year++;
            month -= 12;
        }
        else if (month <= 0) {
            year--;
            month += 12;
        }
        return new Date(day, month, year);
    }

    public boolean isAfter(Date other) {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        LocalDate otherDate = LocalDate.of(other.year, other.month, other.day);
        return date.isAfter(otherDate);
    }

    public boolean isBefore(Date other) {
        LocalDate date = LocalDate.of(this.year, this.month, this.day);
        LocalDate otherDate = LocalDate.of(other.year, other.month, other.day);
        return date.isBefore(otherDate);
    }


    public boolean isEqualsWith(Date other) {
        return day == other.day && month == other.month && year == other.year;
    }


    public boolean isInBetween(Date _beginDate, Date _endDate) {
        LocalDate beginDate = LocalDate.of(_beginDate.year, _beginDate.month, _beginDate.day);
        LocalDate middleDate = LocalDate.of(this.year, this.month, this.day);
        LocalDate endDate = LocalDate.of(_endDate.year, _endDate.month, _endDate.day);
        return !middleDate.isBefore(beginDate) && middleDate.isBefore(endDate);
    }

    /**
     * Get today's date from Calendar class
     * @return new today's date.
     */
    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return new Date(day, month+1, year);
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }

    public boolean equals(Date date) {
        return date.getDay() == day && date.getMonth() == month && date.getYear() == year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}
