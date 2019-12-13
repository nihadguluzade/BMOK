import java.util.ArrayList;
import java.util.Comparator;

public class Location {

	private String locationName;
	private ArrayList<Screen> screenList;
	private int numberOfScreens; // size of screens arrayList
	private int coefficient; // value of location
	private int income = 0; // accoring to the number of screens
	
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
			screenList.add(new Screen(this, (short) i));
		}
	}

	/**
	 * Finds appropriate screen according to minimum crowd rate. Sorts the screens array to give the best close crowd rate.
	 * @param minCrowdRate provided by ad request from client.
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

	/**
	 * Prints the screens if there are exist ads and if the report date is in the between of the ad's beginning and end date.
	 * @param reportDate Every month date of report.
	 * @param currentIncome Overall income, if 0, it indicates that it is the first month of the report; necessary in
	 * specific request cases.
	 */
	public void reportScreens(Date reportDate, int currentIncome) {
		for (Screen s: screenList) {
			if (s.getNumberOfAds() != 0 && s.checkAdBeginDate(reportDate)) // checks if ad's beginning date is equal to the report date
			{
				if (currentIncome == 0) s.checkClientMoney(reportDate); // indicates that it's the 1st month of report and calculates client's budget
				java.lang.System.out.print("\nLOC: " + locationName + "\t\t" + "VALUE: " + coefficient + "↑" + "\t\t");
				s.report(reportDate); // prints ads which are in this location
			}
		}
	}

	/* Utility method to print all screens. */
	public void printScreens() {
		java.lang.System.out.println(locationName + "\t" + coefficient + "↑");
		for (int i = 0; i < numberOfScreens; ++i) {
			screenList.get(i).print();
		}
		java.lang.System.out.println();
	}

	/**
	 * Helps to generate the prices of screens according to the location's coefficient.
	 */
	public boolean coefficientRange(int start, int end) {
		return start <= coefficient && coefficient <= end;
	}

	public ArrayList<Screen> getScreenList() {
		return screenList;
	}

	public void setNumberOfScreens(int numberOfScreens) {
		this.numberOfScreens = numberOfScreens;
	}

	public int getNumberOfScreens() {
		return numberOfScreens;
	}

	public String getLocationName() {
		return locationName;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}
}