import java.util.ArrayList;
import java.util.Comparator;

public class Location {

	private String locationName;
	private ArrayList<Screen> screenList;
	private int numberOfScreens; // size of screens ArrayList
	private int coefficient; // will be used to determine values of screens
	private short screenNumber = 0;
	
	public Location(String locationName, int coefficient) {
		this.locationName = locationName;
		this.coefficient = coefficient;
		screenList = new ArrayList<>();
	}

	/**
	 * Creates the screens with random crowd size.
	 */
	public void createScreens() {
		for (int i = 0; i < numberOfScreens; ++i) {
			screenList.add(new Screen(locationName, screenNumber++));
		}
	}

	/**
	 * Find appropriate screen according to minimum crowd rate.
	 * @param minCrowdRate provided by ad request
	 */
	public Screen findScreen(int minCrowdRate) {
		screenList.sort(Comparator.comparing(Screen::getCrowd)); // sort the arraylist according to crowd size
		for (Screen s : screenList) {
			if (minCrowdRate <= s.getCrowd()) {
				return s;
			}
		}
		return null;
	}

	public void printScreens() {
		java.lang.System.out.println(locationName);
		for (int i = 0; i < numberOfScreens; ++i) {
			screenList.get(i).print();
		}
		java.lang.System.out.println();
	}

	public int getNumberOfScreens() {
		return numberOfScreens;
	}

	public void setNumberOfScreens(int numberOfScreens) {
		this.numberOfScreens = numberOfScreens;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public ArrayList<Screen> getScreenList() {
		return screenList;
	}

	public void setScreenList(ArrayList<Screen> screenList) {
		this.screenList = screenList;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}
}
