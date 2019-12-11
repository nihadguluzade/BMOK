import java.util.ArrayList;
import java.util.Comparator;

public class Location {

	private String locationName;
	private ArrayList<Screen> screens;
	private int capacity; // size of screens ArrayList
	private int coefficient; // will be used to determine values of screens
	
	public Location(String locationName, int coefficient) {
		this.locationName = locationName;
		this.coefficient = coefficient;
		screens = new ArrayList<>();
	}

	/**
	 * Creates the screens with random crowd size.
	 */
	public void createScreens() {
		for (int i = 0; i < capacity; ++i)
			screens.add(new Screen());
	}

	public Screen findScreen(int minCrowdRate) {
		screens.sort(Comparator.comparing(Screen::getCrowd)); // sort the arraylist according to crowd size
		for (Screen s : screens) {
			if (!s.isScreenFull() && minCrowdRate <= s.getCrowd()) {
				return s;
			}
		}
		return null;
		// todo: bu bir denemedir
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public ArrayList<Screen> getScreensArray() {
		return screens;
	}

	public void setScreensArray(ArrayList<Screen> screensArray) {
		this.screens = screensArray;
	}

	public ArrayList<Screen> getScreens() {
		return screens;
	}

	public void setScreens(ArrayList<Screen> screens) {
		this.screens = screens;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}
}
