import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class LocationTest {

    private ArrayList<Screen> screens = new ArrayList<>();
    private int capacity;


    @Test
    public void createScreensTest() {

        screens.ensureCapacity(5);

        capacity = 5;

        int counter = 0;

        for (int i = 0; i < capacity; i++) {
            screens.add(new Screen());
            counter++;
        }

        for (Screen s: screens)
            java.lang.System.out.println(s.getCrowd());

        Assert.assertEquals(1, counter);

    }
}