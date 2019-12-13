/* BMOK Projesi, 14.12.2019 */

import java.util.ArrayList;
import java.util.Objects;

public class System {

	private static ArrayList<Location> locations = new ArrayList<>();
	private static ArrayList<Client> clients = new ArrayList<>();

	private final static int currentDay = Date.getToday().getDay();
	private final static int currentMonth = Date.getToday().getMonth();
	private final static int currentYear = Date.getToday().getYear();

	private final static int maxYear = 2028; // requests above this year will not be approved

	private static void createLocations() {

		locations.add(new Location("Esenler", 1));
		locations.add(new Location("Gungoren", 2));
		locations.add(new Location("Merter", 3));
		locations.add(new Location("Karakoy", 4));
		locations.add(new Location("Uskudar", 5));
		locations.add(new Location("Besiktas", 6));
		locations.add(new Location("Kadikoy", 7));
		locations.add(new Location("Levent", 8));
		locations.add(new Location("Maslak", 9));
		locations.add(new Location("Bebek", 10));
	}

	private static void createScreens() {

		locations.get(0).setNumberOfScreens(2);
		locations.get(0).createScreens();

		locations.get(1).setNumberOfScreens(2);
		locations.get(1).createScreens();

		locations.get(2).setNumberOfScreens(2);
		locations.get(2).createScreens();

		locations.get(3).setNumberOfScreens(2);
		locations.get(3).createScreens();

		locations.get(4).setNumberOfScreens(2);
		locations.get(4).createScreens();

		locations.get(5).setNumberOfScreens(2);
		locations.get(5).createScreens();

		locations.get(6).setNumberOfScreens(2);
		locations.get(6).createScreens();

		locations.get(7).setNumberOfScreens(2);
		locations.get(7).createScreens();

		locations.get(8).setNumberOfScreens(2);
		locations.get(8).createScreens();

		locations.get(9).setNumberOfScreens(2);
		locations.get(9).createScreens();

	}

	private static void createClients() {

		clients.add(new Client("Ahmet", 3000));
		clients.add(new Client("Berk", 6000));

	}

	/** Returns client index from 'clients' arrayList given parameter name. */
	private static Client getClient(String name) {

		for (Client i: clients)
			if (i.getName().equals(name))
				return i;
		return null;
	}

	/** Checks if this location created by looking in 'locations' arrayList. */
	private static Location checkRequestLocation(String location) {

		for (Location l: locations) {
			if (l.getLocationName().equals(location))
				return l;
		}
		return null;

	}

	/** Requests above 12 months must not be approved. */
	private static boolean checkRequestDuration(Request request) {
		return request.getDuration() <= 12;
	}

	/**
	 * Checks if request date is entered right; requests which beginning date is before than the real-time today
	 * must not be approved.
	 */
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

	/** Checks if crowd rate is entered right. Minimum = 1, maximum = 100 */
	private static boolean checkRequestCrowdRate(Request request) {
		return request.getMinCrowdRate() > 0 && request.getMinCrowdRate() <= 100;
	}

	/**
	 * Main method that creates request of client. Before accepting request several controls are performed with the
	 * methods above.
	 * @param clientName name of the client that wants to put ad
	 * @param location location where client wants to put ad
	 * @param request of the client
	 */
	private static void createRequest(String clientName, String location, Request request) {

		// checks if entered client exists
		if (getClient(clientName) == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					"in " + location + ": Invalid client name.");
			return;
		}

		// checks if entered location exists, if yes sets request location
		if (checkRequestLocation(location) == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": We don't offer this location.");
			return;
		} else request.setLocation(checkRequestLocation(location));


		// checks if maximum duration entered is 12
		if (!checkRequestDuration(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": Sorry, the duration limit is 1 year.");
			return;
		}

		// checks if date is entered right
		if (!checkRequestDate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": Invalid date");
			return;
		}

		// checks if minimum crowd rate is 1 or maximum is 100
		if (!checkRequestCrowdRate(request)) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + clientName +
					" in " + location + ": Sorry, crowd rate must be between 1 and 100.");
			return;
		}

		// if everything ok, this request is correct and will be attached to client now
		Objects.requireNonNull(getClient(clientName)).addRequest(request);
		putAd(getClient(clientName), request); // stage to put ad into the screen
	}

	/**
	 * Before putting ad on screen, checks if there's a screen with the crowd size that client wants, and checks
	 * if client has enough money to put the ad. If approved, client's budget is decreased and ad is put on particular screen.
	 */
	private static void putAd(Client client, Request request) {

		// checks the crowd rate
		Screen screen = request.getLocation().findScreen(request.getMinCrowdRate());

		if (screen == null) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + client.getName() +
					": Screen with this crowd size is not available in " + request.getLocation().getLocationName());
			return;
		}

		// checks the client's budget
		if (client.getBudget() < screen.getPrice() * request.getDuration()) {
			java.lang.System.out.println("DECLINED request " + request.getAdName() + " from " + client.getName() +
					" in " + request.getLocation().getLocationName() + ": Not enough budget. (Required: " +
					screen.getPrice() * request.getDuration() + "₺ more)");
			return;
		}

		client.setBudget(client.getBudget() - screen.getPrice() * request.getDuration()); // client gives $
		screen.addAd(request); // advertise is put on screen
		request.setClient(client); // client is attached to request now
		request.generateEndDate(); // end date according to duration is generated
	}

	/* Utility method to print all screens. */
	private static void showAll() {
		java.lang.System.out.println();
		for (Location l: locations) {
			l.printScreens();
		}
	}

	/** Prints brief information about clients, locations and screens is shown at startup. */
	private static void showIntro() {
		java.lang.System.out.println("\nCLIENTS:");
		for (Client c: clients)
		{
			if (c.getNumberOfRequests() > 0) // only clients with request are printed
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

	/**
	 * Generates 1 year (can be changed) report of the system that prints the ads, budget, income, etc.. information.
	 * Start date can be changed. Day of the start date will not affect the requests' beginning date day.
	 */
	private static void report() {

		Date startDate = new Date(1,1,2020); // works for every date
		java.lang.System.out.println("\nREPORT FROM " + startDate.toString() + " TO " + startDate.sumMonths(12).toString() + "\n");
		startDate.sumMonths(-12); // get back to start date

		int income, yearlyIncome = 0;
		for (int i = 0; i < 12; i++)
		{
			java.lang.System.out.println("\n\t\t\t\t\t\t\t\t" + (i + 1) + ". MONTH (" +	startDate.toString() + " - " + startDate.sumMonths(1).toString() + ")");

			income = 0;
			for (Location l: locations)
			{
				l.setIncome(0); // different income for different locations
				startDate.sumMonths(-1); // get back the start date; value is changed above while printing
				l.reportScreens(startDate, yearlyIncome); // prints locations and screens
				startDate.sumMonths(1); // indicates end of report date for this month
				income += l.getIncome(); // store the overall income for this month
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

		createRequest("Berk", "Kadikoy", new Request("Spotify", 50, new Date(1,11,2020), 1));
		createRequest("Berk", "Besiktas", new Request("Tidal", 30, new Date(1,11,2020), 1));
		createRequest("Ahmet", "Esenler", new Request("Netflix", 10, new Date(1,1,2020), 5));
		createRequest("Ahmet", "Gungoren", new Request("Instagram", 62, new Date(1,6,2020), 3));

		showIntro();
		report();
	}

}
