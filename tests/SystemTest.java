import org.junit.Assert;
import org.junit.Test;

public class SystemTest {

    @Test
    public void createRequestDateTest() {
        boolean result = System.checkRequestDate(new Request("rt1", 10, new Date(1,1,2000), 1));
        Assert.assertEquals(result, true);
    }

}