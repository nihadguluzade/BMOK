import java.util.Calendar;

public class Date {

    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Is used to clone object, useful while reporting.
     */
    public Date(Date otherDate) {
        this.day = otherDate.day;
        this.month = otherDate.month;
        this.year = otherDate.year;
    }

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

    /**
     * Checks if the today's time is after than the time represented
     * by the argument.
     * @param other Date we want to compare to.
     * @return true if @param is after than today
     */
    public boolean isAfterThan(Date other) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.set(other.getYear(), other.getMonth(), other.getDay());
        return calendar.compareTo(otherCalendar) > 0;
    }

    /**
     * Checks if the today's time is before than the time represented
     * by the argument.
     * @param other Date we want to compare to.
     * @return true if @param is before than today
     */
    public boolean isBeforeThan(Date other) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.set(other.getYear(), other.getMonth(), other.getDay());
        return calendar.compareTo(otherCalendar) < 0;
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

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }
}
