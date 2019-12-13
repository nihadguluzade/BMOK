import java.util.ArrayList;
import java.util.Iterator;

public class Screen {

	private String screenId;
	private int crowd;
	private ArrayList<Request> ads;
	private int numberOfAds = 0;
	private int price;

	public Screen(Location location, short number) {
		screenId = location.getLocationName() + "-" + number;
		this.crowd = (int) (Math.random() * 100 + 1);
		ads = new ArrayList<>();
		price = location.getCoefficient() * crowd; // todo: write correct formula
	}

	public void addAd(Request ad) {
		ads.add(ad);
		numberOfAds++;
	}

	public void print() {
		java.lang.System.out.println("\t" + screenId + " (crowd: " + crowd + ", " + price + "₺) :");
		if (numberOfAds != 0) {
			for (Request r : ads) {
				java.lang.System.out.println("\t" + r.getAdName() + "\t\t" +
						r.getBeginDate().toString() + " - " + r.getEndDate().toString() + "\t\t" +
						"crowd: " + r.getMinCrowdRate() + "\t" + r.getClient().getName() + "\t" + r.getClient().getBudget() + "₺");
			}
		}
		else {
			java.lang.System.out.println("\t" + "--empty--");
		}
	}

	public void report(int month, int year) {

		if (numberOfAds != 0) {

			java.lang.System.out.println("SCREEN: " + screenId + "\t\tCROWD: " + crowd + "\t\t" + "MONTHLY: " + price + "₺" + "\t\t");

			Iterator<Request> iterator = ads.iterator(); // iterator is useful to avoid ConcurrentModificationException while removing ad from screen
			int profit = 0;
			int initialNumberOfAds = numberOfAds;
			Request tempAd = ads.get(0);

			while (iterator.hasNext()) {
				Request ad = iterator.next();

				//if (ad.getBeginDate().getMonth() <= month && (ad.getBeginDate().getYear() >= year || ad.getEndDate().getYear() == year))
				if (ad.isAdStarted(month, year))
				{
					ad.getClient().setInitialBudget(ad.getClient().getInitialBudget() - price); // calculate the client's budget

					java.lang.System.out.println("AD:  " + ad.getAdName() + "\t\t" +
							ad.getBeginDate().toString() + "\t\t" +
							ad.getEndDate().toString() + "\t\t" +
							"MINCROWD: " + ad.getMinCrowdRate() + "\t\t" +
							"CLIENT: " + ad.getClient().getName() + "\t" +
							"BUDGET: " + ad.getClient().getInitialBudget() + "₺");

					if (ad.getDuration() < 12) {
						if (month == ad.getEndDate().getMonth() - 1 && year == ad.getEndDate().getYear()) { // remove ad from screen if deadline came
							iterator.remove();
							numberOfAds--;
						}
					}
					else  {
						if (year != ad.getBeginDate().getYear() && month == ad.getBeginDate().getMonth()) {
							iterator.remove();
							numberOfAds--;
						}
					}
				}
			}

			profit = price * initialNumberOfAds;
			tempAd.getLocation().setIncome(profit); // doesn't matter which index, we only need to access location
		}
	}

	public boolean checkAdBeginDate(int month, int year) {
		for (Request ad: ads) {
			if (ad.isAdStarted(month, year)) return true;
		}
		return false;
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
