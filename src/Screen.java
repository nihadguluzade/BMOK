import java.util.ArrayList;
import java.util.Iterator;

public class Screen {

	private String screenId; // name of the screen to differ them
	private int crowd; // average crowd rate of the screen
	private ArrayList<Request> ads; // ads that it holds; can hold many because screens are LED
	private int numberOfAds = 0; // counts the number of ads in arraylist
	private int price; // randomly generated price

	public Screen(Location location, short number) {
		screenId = location.getLocationName() + "-" + number;
		this.crowd = (int) (Math.random() * 100 + 1);
		ads = new ArrayList<>();
		price = location.getCoefficient() * crowd;
		if (location.coefficientRange(1,4)) price += 150;
		else if (location.coefficientRange(5,7)) price += 450;
		else if (location.coefficientRange(8,10)) price += 600;
	}

	/** Adds ad to the 'ads' arraylist. */
	public void addAd(Request ad) {
		ads.add(ad);
		numberOfAds++;
	}

	/**
	 * Prints all the ads which beginning date is arrived and removes them after end date came.
	 * @param reportDate
	 */
	public void report(Date reportDate) {

		java.lang.System.out.println("SCREEN: " + screenId + "\t\tCROWD: " + crowd + "\t\t" + "MONTHLY: " + price + "₺" + "\t\t");

		Iterator<Request> iterator = ads.iterator(); // iterator is useful to avoid ConcurrentModificationException while removing ad from screen
		int profit = 0; // stores income from ads for this location monthly
		int initialNumberOfAds = numberOfAds; // otherwise, wouldn't be able to calculate income
		Request tempAd = ads.get(0); // helps to assign income to location

		while (iterator.hasNext())
		{
			Request ad = iterator.next();

			ad.getClient().setInitialBudget(ad.getClient().getInitialBudget() - price); // calculate the client's budget

			// prints the ad
			java.lang.System.out.println("AD:  " + ad.getAdName() + "\t\t" +
					ad.getBeginDate().toString() + "\t\t" +
					ad.getEndDate().toString() + "\t\t" +
					"MINCROWD: " + ad.getMinCrowdRate() + "\t\t" +
					"CLIENT: " + ad.getClient().getName() + "\t" +
					"BUDGET: " + ad.getClient().getInitialBudget() + "₺");

			if (reportDate.sumMonths(1).isEqualsWith(ad.getEndDate())) { // check the report end date
				iterator.remove(); // remove the ad
				numberOfAds--;
			}
			reportDate.sumMonths(-1); // restore to start date
		}

		profit = price * initialNumberOfAds; // calculate the income
		tempAd.getLocation().setIncome(profit); // assign income to the location so we can print it in another class
	}

	/**
	 * Checks if the particular date came for the ad. If yes, print it.
	 * @param reportDate monthly date of report
	 */
	public boolean checkAdBeginDate(Date reportDate) {
		for (Request ad: ads) {
			if (ad.isAdStarted(reportDate)) return true;
		}
		return false;
	}

	/**
	 * In the cases of request's end date is in the next year of beginning date's year, and the report starts in next year,
	 * calculates overall client's budget from beginning till the report's starting date.
	 */
	public void checkClientMoney(Date reportDate) {
		for (Request ad: ads) {
			if (!ad.getBeginDate().isAfter(reportDate))
			{
				Date tempDate = new Date(ad.getBeginDate()); // stores the months between years
				int counter;

				// get the number of months between the ad begin date and 1st month of report date
				for (counter = 0; !tempDate.isEqualsWith(reportDate); counter++) tempDate.sumMonths(1);

				// calculate the budget of the client
				ad.getClient().setInitialBudget(ad.getClient().getInitialBudget() - price * counter);
			}
		}
	}

	/* Utility method to print all ads on the screen. */
	public void print() {
		java.lang.System.out.println("\t" + screenId + " (crowd: " + crowd + ", " + price + "₺) :");
		if (numberOfAds != 0) {
			for (Request r : ads) {
				java.lang.System.out.println("\t" + r.getAdName() + "\t\t" +
						r.getBeginDate().toString() + " - " + r.getEndDate().toString() + "\t\t" +
						"crowd: " + r.getMinCrowdRate() + "\t" + r.getClient().getName() + "\t" + r.getClient().getBudget() + "₺");
			}
		} else
			java.lang.System.out.println("\t" + "--empty--");
	}

	public String getScreenId() {
		return screenId;
	}

	public int getNumberOfAds() {
		return numberOfAds;
	}

	public int getPrice() {
		return price;
	}

	public int getCrowd() {
		return crowd;
	}
}
