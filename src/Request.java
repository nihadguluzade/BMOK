public class Request {

	private String adName;
	private Location location;
	private int minCrowdRate;
	private Date beginDate;
	private int duration; // in month
	private Date endDate; // system generates it
	private Client client; // system assigns after validating request

	public Request(String adName, int minCrowdRate, Date beginDate, int duration) {
		this.adName = adName;
		this.minCrowdRate = minCrowdRate;
		this.beginDate = beginDate;
		this.duration = duration;
	}

	/**
	 * Generates end date according to the duration provided by client.
	 */
	public void generateEndDate() {
		endDate = new Date(beginDate);
		endDate.sumMonths(duration);
	}

	/**
	 * Checks if ad's begin date is started. Useful in report.
	 */
	public boolean isAdStarted(Date reportDate) {
		return reportDate.isInBetween(beginDate, endDate);
	}

	public Date getEndDate() {
		return endDate;
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

	public Date getBeginDate() {
		return beginDate;
	}

	public int getDuration() {
		return duration;
	}

	public int getMinCrowdRate() {
		return minCrowdRate;
	}

	public String getAdName() {
		return adName;
	}
}
