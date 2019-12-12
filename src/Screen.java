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
						r.getBeginDate().toString() + " - " + r.generateEndDate().toString() + "\t\t" +
						"crowd: " + r.getMinCrowdRate() + "\t" + r.getClient().getName() + "\t" + r.getClient().getBudget() + "₺");
			}
		}
		else {
			java.lang.System.out.println("\t" + "--empty--");
		}
	}

	public void report(int month) {

		if (numberOfAds != 0) {

			java.lang.System.out.println(screenId + "\t\tcrowd: " + crowd + "\t\t" + "monthly: " + price + "₺" + "\t\t");

			Iterator<Request> iterator = ads.iterator(); // iterator is useful to avoid ConcurrentModificationException while removing ad from screen

			while (iterator.hasNext()) {
				Request ad = iterator.next();

				if (ad.getBeginDate().getMonth() <= month && month < ad.generateEndDate().getMonth())
				{
//					Date monthStartDate = ad.getBeginDate(); // doesn't create new object
					Date monthStartDate = new Date(ad.getBeginDate());
					monthStartDate.setMonth(month);
					Date monthEndDate = monthStartDate.sumMonths(1);

					ad.getClient().setInitialBudget(ad.getClient().getInitialBudget() - price);

					java.lang.System.out.println("Ad:  " + ad.getAdName() + "\t\t" +
							monthStartDate.toString() + "\t\t" + monthEndDate.toString() + "\t\t" +
							"crowd: " + ad.getMinCrowdRate() + "\t\t" + ad.getClient().getName() + "\t" +
							ad.getClient().getInitialBudget() + "₺");

					if (monthEndDate.equals(ad.generateEndDate())) { // remove ad from screen if deadline came
						iterator.remove();
						numberOfAds--;
					}
				}
			}
		}
	}

	public boolean checkAdBeginDate(int month) {
		for (Request ad: ads) {
			if (ad.isAdStarted(month)) return true;
		}
		return false;
	}

	public ArrayList<Request> getAds() {
		return ads;
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
