import java.util.ArrayList;
import java.util.Objects;

public class System {

	private static ArrayList<Location> locations = new ArrayList<>();
	private static ArrayList<Client> clients = new ArrayList<>();

	private final static int currentDay = Date.getToday().getDay();
	private final static int currentMonth = Date.getToday().getMonth();
	private final static int currentYear = Date.getToday().getYear();

	private final static int maxYear = 2028;

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

	private static Location checkRequestLocation(String location) {

		for (Location l: locations) {
			if (l.getLocationName().equals(location))
				return l;
		}
		return null;

	}

	private static boolean checkRequestDuration(Request request) {
		return request.getDuration() <= 12;
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

	/**
	 * Main function to create request of client. Before accepting request several controls are
	 * performed with the methods above.
	 * @param clientName name of the client that wants to put ad
	 * @param location location where client wants to put ad
	 * @param request of the client
	 */
	private static void createRequest(String clientName, String location, Request request) {

		if (getClient(clientName) == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					"in " + location + ": Invalid client name.");
			return;
		}

		if (checkRequestLocation(location) == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": We don't offer this location.");
			return;
		} else request.setLocation(checkRequestLocation(location));

		if (!checkRequestDuration(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": Sorry, the duration limit is 1 year.");
			return;
		}

		if (!checkRequestDate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": Invalid date");
			return;
		}

		if (!checkRequestCrowdRate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": Sorry, crowd rate must be between 1 and 100.");
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
					": Screen with this crowd size is not available in " + request.getLocation().getLocationName());
			return;
		}

		if (client.getBudget() < screen.getPrice() * request.getDuration()) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + client.getName() +
					" in " + request.getLocation().getLocationName() + ": Not enough budget. (Required: " + screen.getPrice() * request.getDuration() + "₺)");
			return;
		}

		client.setBudget(client.getBudget() - screen.getPrice() * request.getDuration());
		screen.addAd(request);
		request.setClient(client);
		request.generateEndDate();
	}

	private static void showAll() {
		java.lang.System.out.println();
		for (Location l: locations) {
			l.printScreens();
		}
	}

	private static void showIntro() {
		java.lang.System.out.println("\nCLIENTS:");
		for (Client c: clients)
		{
			if (c.getNumberOfRequests() > 0)
			{
				java.lang.System.out.println(c.getName() + ": " + c.getInitialBudget() + "₺" + ", Requests: " +
						c.getNumberOfRequests());

				for (Request r : c.getRequestedAds()) {
					java.lang.System.out.println("Name: " + r.getAdName() + "\t\t" + r.getLocation().getLocationName() +
							"\t\tBegin: " + r.getBeginDate().toString() + "\t\tDuration: " + r.getDuration() + "\t\tMinCrowd: " + r.getMinCrowdRate());
				}
				java.lang.System.out.println();
			}
		}

		java.lang.System.out.println("LOCATIONS:");
		for (Location l: locations) {
			java.lang.System.out.println(l.getLocationName() + "\t" + "Value: " + l.getCoefficient() + "↑\t" +
					"Screens: " + l.getNumberOfScreens());
			for (Screen s: l.getScreenList()) {
				java.lang.System.out.println("Name: " + s.getScreenId() + ", crowd: " + s.getCrowd() + ", price: " + s.getPrice() + "₺");
			}
			java.lang.System.out.println("");
		}
	}

	private static void report() {

		Date startDate = new Date(1,1,2020);
		java.lang.System.out.println("\nREPORT FROM " + startDate.toString() + " TO " + startDate.sumMonths(12).toString() + "\n");
		startDate.sumMonths(-12); // get back to start date



		int income, yearlyIncome = 0;
		for (int i = 0; i < 12; i++)
		{
			java.lang.System.out.println("\n\t\t\t\t\t\t\t\t" + (i + 1) + ". MONTH (" +	startDate.toString() + " - " + startDate.sumMonths(1).toString() + ")");
			income = 0;

			for (Location l: locations)
			{
				l.setIncome(0);
//				l.reportScreens(i + 1, startDate.getYear());
				startDate.sumMonths(-1);
				l.reportScreens(startDate.getMonth(), startDate.getYear());
				startDate.sumMonths(1);
				income += l.getIncome();
			}
			java.lang.System.out.println("\nIncome: " + income + "₺");
			yearlyIncome += income;
		}
		java.lang.System.out.println("\nYearly Income: " + yearlyIncome + "₺");
	}

	public static void main(String[] args) {

		createLocations();
		createScreens();
		createClients();

		createRequest("Ahmet", "Esenler", new Request("Hepsiburada", 20, new Date(1,10,2020), 4));
//		createRequest("Berk", "Davutpasa", new Request("Trendyol", 55, new Date(1,1,2020), 11));

//		showAll();
		showIntro();
		report();
	}

}
