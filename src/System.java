import java.util.ArrayList;
import java.util.Objects;

public class System {

	private static ArrayList<Location> locations = new ArrayList<>();
	private static ArrayList<Client> clients = new ArrayList<>();
	private static ArrayList<Client> requests = new ArrayList<>();

	private final static int currentDay = Date.getToday().getDay();
	private final static int currentMonth = Date.getToday().getMonth();
	private final static int currentYear = Date.getToday().getYear();

	private final static int maxYear = 2025;

	private static void createLocations() {

		locations.add(new Location("Davutpasa", 3));
		locations.add(new Location("Esenler", 1));
		locations.add(new Location("Gungoren", 5));
		locations.add(new Location("Cevizlibag", 7));

	}

	private static void createClients() {

		clients.add(new Client("Ahmet", 100));
		clients.add(new Client("Berk", 250));

	}

	private static void createScreens() {

		locations.get(0).setCapacity(4); // set number of screens in Davutpasa
		locations.get(0).createScreens();

		locations.get(1).setCapacity(12); // set number of screens in Esenler
		locations.get(1).createScreens();

		locations.get(2).setCapacity(5); // set number of screens in Gungoren
		locations.get(2).createScreens();

		locations.get(3).setCapacity(10); // set number of screens in Cevizlibag
		locations.get(3).createScreens();

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
			java.lang.System.out.println("Error: invalid date.");
			return;
		}

		if (!checkRequestCrowdRate(request)) {
			java.lang.System.out.println("Sorry, crowd rate must be between 1 and 100.");
			return;
		}


		Objects.requireNonNull(getClient(clientName)).addRequest(request);
		putAd(request);
	}

	public static void putAd(Request request) {

		Screen screen = request.getLocation().findScreen(request.getMinCrowdRate());
		if (screen == null) { // TODO: hata mesaji nasil yazilsin
			java.lang.System.out.println("Screen with these conditions not available currently.");
		}

	}

	public static void main(String[] args) {

		createLocations();
		createScreens();
		createClients();

		createRequest("Ahmet", new Request("Davutpasa", new Date(1,1,2020), 1, 50, "Trendyol"));

	}

}
