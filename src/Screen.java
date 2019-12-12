import java.util.ArrayList;

public class Screen {

	private String screenId;
	private int crowd;
	private ArrayList<Request> ads;
	private int capacity;
	private int numberOfAds = 0;

	public Screen(String location, short number) {
		screenId = location + "-" + number;
		this.crowd = (int) (Math.random() * 100 + 1);
		ads = new ArrayList<>();
	}

	public void addAd(Request ad) {
		ads.add(ad);
		numberOfAds++;
	}

	public void print() {
		java.lang.System.out.println("\t" + screenId + " (" + crowd + ") :");
		if (numberOfAds != 0) {
			for (Request r : ads) {
				java.lang.System.out.println("\t" + r.getAdName() + "\t\t" +
						r.getBeginDate().toString() + " - " + r.generateEndDate().toString() + "\t\t" + r.getDuration() + "\t" +
						r.getMinCrowdRate() + "\t" + r.getClient().getName());
			}
		}
		else {
			java.lang.System.out.println("\t" + "--empty--");
		}
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public boolean isScreenFull() {
		return numberOfAds == capacity;
	}

	public int getNumberOfAds() {
		return numberOfAds;
	}

	public void setNumberOfAds(int numberOfAds) {
		this.numberOfAds = numberOfAds;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCrowd() {
		return crowd;
	}

	public void setCrowd(int crowd) {
		this.crowd = crowd;
	}

	public ArrayList<Request> getAds() {
		return ads;
	}
}
