import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class DateTest {

    private int day;
    private int month;
    private int year;

    private Date date1 = new Date(1,2,2020);
    private Date e_date1 = new Date(1,3,2020);
    private Date date2 = new Date(12,4,2020);
    private Date e_date2 = new Date(12,5,2020);
    private Date date3 = new Date(1,11,2020);
    private Date e_date3 = new Date(1,12,2020);

    @Test
    public void sumMonths() {
        short month = 1;
        Calendar calendar = Calendar.getInstance();

        calendar.set(date1.getYear(), Calendar.MARCH, date1.getYear());
        calendar.add(Calendar.MONTH, month);
        date1.setMonth(Calendar.MONTH);

        calendar.set(date2.getYear(), date2.getMonth(), date2.getYear());
        calendar.add(Calendar.MONTH, month);
        date2.setMonth(Calendar.MONTH);

        calendar.set(date3.getYear(), date3.getMonth(), date3.getYear());
        calendar.add(Calendar.MONTH, month);
        date3.setMonth(Calendar.MONTH);

        Assert.assertEquals(e_date1.getDay(), date1.getDay());
        Assert.assertEquals(e_date1.getMonth(), date1.getMonth());
        Assert.assertEquals(e_date1.getYear(), date1.getYear());

        Assert.assertEquals(e_date2.getDay(), date2.getDay());
        Assert.assertEquals(e_date2.getMonth(), date2.getMonth());
        Assert.assertEquals(e_date2.getYear(), date2.getYear());

        Assert.assertEquals(e_date3.getDay(), date3.getDay());
        Assert.assertEquals(e_date3.getMonth(), date3.getMonth());
        Assert.assertEquals(e_date3.getYear(), date3.getYear());

    }

    @Test
    public void compareDates() {

        Date d1 = new Date(15,6,2000);
        Date d2 = new Date(15,6,2000);

        Assert.assertEquals(true, d1.equals(d2));

    }
}