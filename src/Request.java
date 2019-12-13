public class Request {

	private String adName;
	private Location location;
	private int minCrowdRate;
	private Date beginDate;
	private int duration; // in month
	private Date endDate;
	private Client client;

	public Request(String adName, int minCrowdRate, Date beginDate, int duration) {
		this.adName = adName;
		this.minCrowdRate = minCrowdRate;
		this.beginDate = beginDate;
		this.duration = duration;
	}

	public void printAd() {
		java.lang.System.out.println(location.getLocationName() + " ");
	}

	public void generateEndDate() {
		endDate = new Date(beginDate);
		endDate.sumMonths(duration);
	}

	/**
	 * Check if ad begin date is started. Useful in report.
	 */
	public boolean isAdStarted(int month, int year) {
//		java.lang.System.out.println(beginDate.getMonth() <= month);
//		java.lang.System.out.println(beginDate.getYear() <= year);
//		java.lang.System.out.println(month < endDate.getMonth());
//		java.lang.System.out.println(year <= endDate.getYear());
//		if ( (beginDate.getMonth() <= month && beginDate.getYear() <= year) && (month < endDate.getMonth() || year <= endDate.getYear())) return true;
		if ( (beginDate.getMonth() <= month && beginDate.getYear() <= year)  ) {
			if (year < endDate.getYear())
				return true;
			else if (year == endDate.getYear()) {
				if (month < endDate.getMonth()) return true;
			}
		}

		return false;

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
