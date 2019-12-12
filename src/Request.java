public class Request {

	private Location location;
	private String locationName;
	private Date beginDate;
	private int duration; // in month
	private int minCrowdRate;
	private String adName;
	private Client client;

	public Request(String locationName, Date beginDate, int duration, int minCrowdRate, String adName) {
		this.locationName = locationName;
		this.beginDate = beginDate;
		this.duration = duration;
		this.minCrowdRate = minCrowdRate;
		this.adName = adName;
	}

	public void printAd() {
		java.lang.System.out.println(locationName + " ");
	}

	public Date generateEndDate() {
		return beginDate.sumMonths(duration);
	}

	/**
	 * Check if ad begin date is started. Useful in report.
	 */
	public boolean isAdStarted(int month) {
		return month >= beginDate.getMonth();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getMinCrowdRate() {
		return minCrowdRate;
	}

	public void setMinCrowdRate(int minCrowdRate) {
		this.minCrowdRate = minCrowdRate;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}
}
