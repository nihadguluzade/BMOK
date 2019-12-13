import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    private Date date1 = new Date(1,2,2020);
    private Date date2 = new Date(12,4,2020);
    private Date date4 = new Date(12,8,2017);
    private Date date5 = new Date(12,8,2017);

    private Date beginDate1 = new Date(1,10,2020);
    private Date endDate1 = new Date(1,2,2021);
    private Date reportDate1 = new Date(1,12,2020);

    private Date beginDate2 = new Date(1,10,2020);
    private Date endDate2 = new Date(1,9,2020);
    private Date reportDate2 = new Date(1,12,2020);

    private Date beginDate3 = new Date(1,1,2020);
    private Date endDate3 = new Date(1,2,2020);
    private Date reportDate3 = new Date(1,1,2020);

    @Test
    public void inBetween() {
        assertTrue(reportDate1.isInBetween(beginDate1, endDate1));
        assertFalse(reportDate2.isInBetween(beginDate2, endDate2));
        assertTrue(reportDate3.isInBetween(beginDate3, endDate3));
    }

    @Test
    public void isBefore() {
        assertTrue(date1.isBefore(date2));
        assertFalse(date4.isBefore(date5));
    }

    @Test
    public void isAfter() {
        assertFalse(date1.isAfter(date2));
        assertTrue(date2.isAfter(date5));
        assertFalse(date4.isAfter(date5));
    }

    @Test
    public void isEqualsWith() {
        assertTrue(date4.isEqualsWith(date5));
    }

    @Test
    public void compareDates() {
        Date d1 = new Date(15,6,2000);
        Date d2 = new Date(15,6,2000);
        assertTrue(d1.equals(d2));

    }
}