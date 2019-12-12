import java.util.ArrayList;
import java.util.Objects;

public class System {

	private static ArrayList<Location> locations = new ArrayList<>();
	private static ArrayList<Client> clients = new ArrayList<>();

	private final static int currentDay = Date.getToday().getDay();
	private final static int currentMonth = Date.getToday().getMonth();
	private final static int currentYear = Date.getToday().getYear();

	private final static int maxYear = 2025;

	private static void createLocations() {

		locations.add(new Location("Davutpasa", 3));
		locations.add(new Location("Esenler", 1));
		locations.add(new Location("Gungoren", 5));

	}

	private static void createScreens() {

		locations.get(0).setNumberOfScreens(4); // set number of screens in Davutpasa
		locations.get(0).createScreens();

		locations.get(1).setNumberOfScreens(3); // set number of screens in Esenler
		locations.get(1).createScreens();

		locations.get(2).setNumberOfScreens(1); // set number of screens in Gungoren
		locations.get(2).createScreens();

	}

	private static void createClients() {

		clients.add(new Client("Ahmet", 100));
		clients.add(new Client("Berk", 250));
		clients.add(new Client("Caner", 50));

	}

	private static Client getClient(String name) {

		for (Client i: clients)
			if (i.getName().equals(name))
				return i;
		return null;
	}

	public static Location checkRequestLocation(Request request) {

		for (Location l: locations) {
			if (l.getLocationName().equals(request.getLocationName()))
				return l;
		}
		return null;

	}

	public static boolean checkRequestDuration(Request request) {
		return request.getDuration() <= 12;
		// todo: check if input is integer
	}

	public static boolean checkRequestDate(Request request) {

		if (request.getBeginDate().getDay() < 1 || request.getBeginDate().getDay() > 31) return false;

		if (request.getBeginDate().getMonth() < 1 || request.getBeginDate().getMonth() > 12) return false;

		if (request.getBeginDate().getYear() < currentYear || request.getBeginDate().getYear() > maxYear)
			return false;

		if (request.getBeginDate().getYear() == currentYear) {
			return request.getBeginDate().getDay() > currentDay && request.getBeginDate().getMonth() >= currentMonth;
		}

		return true;
	}

	public static boolean checkRequestCrowdRate(Request request) {
		return request.getMinCrowdRate() > 0 && request.getMinCrowdRate() <= 100;
	}

	public static void createRequest(String clientName, Request request) {

		if (getClient(clientName) == null) {
			java.lang.System.out.println("Invalid client name.");
			return;
		}

		if (checkRequestLocation(request) == null) {
			java.lang.System.out.println("Sorry, we don't offer this location.");
			return;
		} else request.setLocation(checkRequestLocation(request));

		if (!checkRequestDuration(request)) {
			java.lang.System.out.println("Sorry, the duration limit is 1 year.");
			return;
		}

		if (!checkRequestDate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName + ": " +
					"Invalid date");
			return;
		}

		if (!checkRequestCrowdRate(request)) {
			java.lang.System.out.println("Sorry, crowd rate must be between 1 and 100.");
			return;
		}

		// todo: add DECLINED message to errors

		/*java.lang.System.out.println("Created request: " + request.getAdName() + " from " + clientName +
				", " + request.getBeginDate().toString() + " - " + request.generateEndDate().toString() +
				", " + request.getMinCrowdRate() + " crowd");*/

		Objects.requireNonNull(getClient(clientName)).addRequest(request);
		putAd(getClient(clientName), request);
	}

	public static void putAd(Client client, Request request) {

		Screen screen = request.getLocation().findScreen(request.getMinCrowdRate());

		if (screen == null) {
			java.lang.System.out.println("Screen with these crowd size is not available in " + request.getLocationName());
			return;
		}

		screen.addAd(request);
		request.setClient(client);
	}

	public static void showAll() {
		java.lang.System.out.println();
		for (Location l: locations) {
			l.printScreens();
		}
	}

	public static void main(String[] args) {

		createLocations();
		createScreens();
		createClients();

		createRequest("Ahmet", new Request("Davutpasa", new Date(1,1,2020), 1, 50, "Trendyol"));
		createRequest("Ahmet", new Request("Esenler", new Date(1,1,2020), 2, 10, "Hepsiburada"));
		createRequest("Ahmet", new Request("Uskudar", new Date(1,3,2020), 1, 67, "n11"));
		createRequest("Berk", new Request("Gungoren", new Date(1,1,2019), 3, 90, "Zara"));

		showAll();

	}

}
