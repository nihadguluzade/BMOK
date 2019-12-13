import java.util.ArrayList;

public class Client {

	private String name;
	private int budget;
	private int initialBudget; // stores initial budget, useful in report
	private ArrayList<Request> requestedAds;
	private int numberOfRequests = 0; // counter for the arrayList

	public Client(String name, int budget) {
		this.name = name;
		this.budget = budget;
		initialBudget = budget;
		requestedAds = new ArrayList<>();
	}

	public void addRequest(Request ad) {
		requestedAds.add(ad);
		numberOfRequests++;
	}

	public ArrayList<Request> getRequestedAds() {
		return requestedAds;
	}

	public int getNumberOfRequests() {
		return numberOfRequests;
	}

	public int getInitialBudget() {
		return initialBudget;
	}

	public void setInitialBudget(int initialBudget) {
		this.initialBudget = initialBudget;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getName() {
		return name;
	}
}
