import java.util.ArrayList;

public class Client {

	private String name;
	private int budget;
	private ArrayList<Request> requestedAds;
	private int counter = 0; // size of ArrayList

	public Client(String name, int budget) {
		this.name = name;
		this.budget = budget;
		requestedAds = new ArrayList<Request>();
	}

	public void addRequest(Request ad) {
		requestedAds.add(ad);
		counter++;
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

	public int getCounter() {
		return counter;
	}
}
