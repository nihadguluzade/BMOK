import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class LocationTest {

    private String locationName = "Kadikoy";
    private ArrayList<Screen> screenList = new ArrayList<>();
    private int numberOfScreens = 2;
    private int coefficient;
    private short screenNumber = 0;

    @Test
    public void createScreens() {
        for (int i = 0; i < numberOfScreens; ++i) {
            screenList.add(new Screen(locationName, screenNumber++));
        }
    }

    @Test
    public void findScreen() {
    }

    @Test
    public void printScreens() {
        java.lang.System.out.println(locationName);
        for (int i = 0; i < numberOfScreens; ++i) {
            screenList.get(i).print();
        }
    }

    @Test
    public void getNumberOfScreens() {
    }

    @Test
    public void setNumberOfScreens() {
    }

    @Test
    public void getLocationName() {
    }

    @Test
    public void setLocationName() {
    }

    @Test
    public void getScreenList() {
    }

    @Test
    public void setScreenList() {
    }

    @Test
    public void getCoefficient() {
    }

    @Test
    public void setCoefficient() {
    }
}