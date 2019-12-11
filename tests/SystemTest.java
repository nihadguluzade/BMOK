import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemTest {

    Request request = new Request("Levent",
            new Date(12, 12, 2020), 1,
            65, "Reklam1");

    @Test
    public void createRequestDateTest() {
        boolean result = System.checkRequestDate(request);
        Assert.assertEquals(result, true);
    }

}