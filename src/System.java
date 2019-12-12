import java.util.ArrayList;
import java.util.Calendar;
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

		clients.add(new Client("Ahmet", 1000));
		clients.add(new Client("Berk", 2000));
		clients.add(new Client("Caner", 50));

	}

	private static Client getClient(String name) {

		for (Client i: clients)
			if (i.getName().equals(name))
				return i;
		return null;
	}

	private static Location checkRequestLocation(Request request) {

		for (Location l: locations) {
			if (l.getLocationName().equals(request.getLocationName()))
				return l;
		}
		return null;

	}

	private static boolean checkRequestDuration(Request request) {
		return request.getDuration() <= 12;
		// todo: check if input is integer
	}

	protected static boolean checkRequestDate(Request request) {

		if (request.getBeginDate().getDay() < 1 || request.getBeginDate().getDay() > 31) return false;

		if (request.getBeginDate().getMonth() < 1 || request.getBeginDate().getMonth() > 12) return false;

		if (request.getBeginDate().getYear() < currentYear || request.getBeginDate().getYear() > maxYear)
			return false;

		if (request.getBeginDate().getYear() == currentYear) {
			return request.getBeginDate().getDay() > currentDay && request.getBeginDate().getMonth() >= currentMonth;
		}

		return true;
	}

	private static boolean checkRequestCrowdRate(Request request) {
		return request.getMinCrowdRate() > 0 && request.getMinCrowdRate() <= 100;
	}

	private static void createRequest(String clientName, Request request) {

		if (getClient(clientName) == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					"in " + request.getLocationName() + ": Invalid client name.");
			return;
		}

		if (checkRequestLocation(request) == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + request.getLocationName() + ": We don't offer this location.");
			return;
		} else request.setLocation(checkRequestLocation(request));

		if (!checkRequestDuration(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + request.getLocationName() + ": Sorry, the duration limit is 1 year.");
			return;
		}

		if (!checkRequestDate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + request.getLocationName() + ": Invalid date");
			return;
		}

		if (!checkRequestCrowdRate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + request.getLocationName() + ": Sorry, crowd rate must be between 1 and 100.");
			return;
		}

		/*java.lang.System.out.println("Created request: " + request.getAdName() + " from " + clientName +
				", " + request.getBeginDate().toString() + " - " + request.generateEndDate().toString() +
				", " + request.getMinCrowdRate() + " crowd");*/

		Objects.requireNonNull(getClient(clientName)).addRequest(request);
		putAd(getClient(clientName), request);
	}

	private static void putAd(Client client, Request request) {

		Screen screen = request.getLocation().findScreen(request.getMinCrowdRate());

		if (screen == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + client.getName() +
					": Screen with this crowd size is not available in " + request.getLocationName());
			return;
		}

		if (client.getBudget() < screen.getPrice() * request.getDuration()) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + client.getName() +
					" in " + request.getLocationName() + ": Not enough budget. (Required: " + screen.getPrice() * request.getDuration() + "₺)");
			return;
		}

		client.setBudget(client.getBudget() - screen.getPrice() * request.getDuration());
		screen.addAd(request);
		request.setClient(client);
	}

	private static void showAll() {
		java.lang.System.out.println();
		for (Location l: locations) {
			l.printScreens();
		}
	}

	private static void report() {

		Date startDate = new Date(1,1,2020);

		java.lang.System.out.println("\nREPORT FROM " + startDate.toString() + " TO " + startDate.sumMonths(12).toString());

		for (Client c: clients) {
			java.lang.System.out.println(c.getName() + ": " + c.getInitialBudget() + "₺");
		}

		for (int i = 0; i < 12; i++) {
			java.lang.System.out.println(i + 1 + ". month\n");
			for (Location l: locations) {
				l.reportScreens(i + 1);
			}
			java.lang.System.out.println();
		}

	}

	public static void main(String[] args) {

		createLocations();
		createScreens();
		createClients();

		createRequest("Ahmet", new Request("Esenler", new Date(1,1,2020), 4, 32, "Hepsiburada"));
		createRequest("Ahmet", new Request("Davutpasa", new Date(1,1,2020), 2, 55, "Trendyol"));
		createRequest("Berk", new Request("Davutpasa", new Date(12,6,2020), 3, 65, "Steam Summer Sale"));

		showAll();
		report();
	}

}
