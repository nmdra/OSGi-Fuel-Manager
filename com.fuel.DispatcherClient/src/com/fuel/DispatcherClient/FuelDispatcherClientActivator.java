package com.fuel.DispatcherClient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.fuel.DispatcherService.FuelDispatcherService;
import com.fuel.dispatchedtransactions.TransactionsService;
import com.fuel.dispatchedtransactions.TransactionsModel;
import java.util.Scanner;
import java.util.List;

public class FuelDispatcherClientActivator implements BundleActivator {
	private FuelDispatcherService fuelDispatcherService;
	private TransactionsService transactionsService;
	private ServiceReference<FuelDispatcherService> dispatcherReference;
	private ServiceReference<TransactionsService> transactionsReference;

	private Scanner scanner = new Scanner(System.in);
	private int pumpNumber;
	private String selectedFuelType;
	private double userAmount;
	private double cost = 0;
	private double Litres =0;
	
	
	final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";
    final String BLUE = "\u001B[34m";
    final String CYAN = "\u001B[36m";
    final String BOLD = "\u001B[1m";

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Fuel Dispatcher Client Start.");
		ServiceReference<FuelDispatcherService> reference = context.getServiceReference(FuelDispatcherService.class);
		ServiceReference<TransactionsService> transactionsRef = context.getServiceReference(TransactionsService.class);
		if (transactionsRef != null) {
			transactionsService = context.getService(transactionsRef);
			System.out.println("💥 Transaction Service available.");
		} else {
			System.out.println("⚠️ Transaction Service not available.");
		}
		if (reference != null) {
			fuelDispatcherService = context.getService(reference);
			System.out.println("💥 Dispatcher Service available.");
		} else {
			System.out.println("⚠️ Dispactcher Service not available.");
		}

		startClient();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Fuel Dispatcher Client Stopped.");
	}

	public void startClient() {
		while (true) {
			System.out.println(" ");
			System.out.println(" ");
			System.out.println( BOLD+"====================================================");
			System.out.println("|      WELCOME TO THE FUEL DISPATCHER SYSTEM       "+RESET+ BOLD+"|");
			System.out.println("====================================================");
			System.out.println("|     1. Go to fuel Distribution                   |");
			System.out.println("|     2. View Transactions                         |");
			System.out.println("|     3. Exit                                      |");
			System.out.println("====================================================");
			System.out.println(RESET+" ");
			System.out.print(BOLD+GREEN+"Please enter your choice: "+RESET+BOLD);
			String choice = scanner.nextLine();
			System.out.println(" ");
			System.out.println(" ");
			switch (choice) {
			case "1":
				selectPump();
				break;
			case "2":
				viewTransactions();
				break;
				
				
				
			case "3":
				System.out.println(" ");
				System.out.println(BOLD+RED+"Exiting Fuel Dispatcher System..."+RESET+BOLD);
				System.out.println(" ");
				
				return;
			default:
				System.out.println(" ");
				System.out.println(BOLD+RED+"Invalid choice. Please enter 1 or 2."+RESET+BOLD);
				System.out.println(" ");
			}
		}
	}

	private void selectPump() {

		System.out.println("====================================================");
		System.out.println("|          THE FUEL DISPATCHER SYSTEM              | ");
		System.out.println("====================================================");
		System.out.println("|     1. Enter Pump Number (1 0r 2 or 3)           |");
		System.out.println("|     2. Type 'e' to exit                          |");
		System.out.println("====================================================");
		System.out.println(" ");
		System.out.print(BOLD+GREEN+"Please enter your choice: "+RESET+BOLD);
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("e")) {
			return;
		}

		try {
			pumpNumber = Integer.parseInt(input);
			fuelDispatcherService.selectPump(pumpNumber);
			selectFuelType();
		} catch (NumberFormatException e) {
			System.out.println(" ");
			System.out.println(BOLD+RED+"Invalid pump number. Please enter a valid number."+RESET+BOLD);
			System.out.println(" ");
			selectPump();
		}
	}
	private void viewTransactions() {
		List<TransactionsModel> transactionsList = transactionsService.getTransactions();
		System.out.println(CYAN + BOLD + "\n🧾 List of Transactions:" + RESET);

		if (transactionsService.getTransactions().isEmpty()) {
			System.out.println(RED + BOLD + "No transactions found." + RESET);
		} else {
			for (TransactionsModel t : transactionsService.getTransactions()) {
				System.out.println(BOLD + GREEN + t.toString() + RESET);
			}
		}

		System.out.println("\nPress Enter to return to main menu.");
		scanner.nextLine();
	}
	private void selectFuelType() {
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("====================================================");
		System.out.println("|            FUEL TYPE SELECTION                   |");
		System.out.println("====================================================");
		System.out.println("|    1. 95 Octane                                  |");
		System.out.println("|    2. 92 Octane                                  |");
		System.out.println("|    3. Diesel                                     |");
		System.out.println("|    4. Petrol                                     |");
		System.out.println("|                                                  |");
		System.out.println("| Enter fuel type number (or type 'e' to go back): |");
		System.out.println("|                                                  |");
		System.out.println("====================================================");
		System.out.println(" ");
		System.out.print(BOLD+GREEN+"Please enter your choice: "+RESET+BOLD);
		String choice = scanner.nextLine();

		switch (choice) {
		case "1":
			selectedFuelType = "95 Octane";
			break;
		case "2":
			selectedFuelType = "92 Octane";
			break;
		case "3":
			selectedFuelType = "Diesel";
			break;
		case "4":
			selectedFuelType = "Petrol";
			break;
		case "e":
			selectPump();
			return;
		default:
			System.out.println(" ");
			System.out.println(BOLD+RED+"Invalid choice. Please select a valid fuel type."+RESET+BOLD);
			System.out.println(" ");
			selectFuelType();
			return;
		}
		fuelDispatcherService.selectFuelType(selectedFuelType);
		enterAmount();
	}

	private void enterAmount() {
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("====================================================");
		System.out.println("|         SELECT AMOUNT ENTRY METHOD               |");
		System.out.println("====================================================");
		System.out.println("|       1. Enter amount in Rupees                  |");
		System.out.println("|       2. Enter amount in Liters                  |");
		System.out.println("|                                                  |");
		System.out.println("|  Enter your choice (or type 'e' to go back):     |");
		System.out.println("====================================================");
		System.out.println(" ");
		System.out.print(BOLD+GREEN+"Please enter your choice: "+RESET+BOLD);
		String choice = scanner.nextLine();
		switch (choice) {
		case "1":
			calculateByRupees();
			break;
		case "2":
			calculateByLiters();
			break;
		case "e":
			selectFuelType();
			break;
		default:
			System.out.println(" ");
			System.out.println(BOLD+RED+"Invalid choice. Please enter 1 or 2."+RESET+BOLD);
			System.out.println(" ");
			enterAmount();
		}
	}

	private void calculateByRupees() {
		System.out.println(" ");
		System.out.println("=====================================================");
		System.out.println("|                                                   |");
		System.out.println("|Enter the amount in Rupees (or type 'e' to go back)|");
		System.out.println("|                                                   |");
		System.out.println("=====================================================");
		System.out.println(" ");
		System.out.print(BOLD+GREEN+"Please enter the amount: "+RESET+BOLD);
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("e")) {
			enterAmount();
			return;
		}

		try {
			userAmount = Double.parseDouble(input);
			double liters = fuelDispatcherService.calculateByRupees(userAmount);
			cost = userAmount;
			confirmTransaction();

		} catch (NumberFormatException e) {
			System.out.println(" ");
			System.out.println(BOLD+RED+"Invalid input. Please enter a valid number."+RESET+BOLD);
			System.out.println(" ");
			calculateByRupees();
		}
	}

	private void calculateByLiters() {
		System.out.println(" ");
		System.out.println("=====================================================");
		System.out.println("|                                                   |");
		System.out.println("|Enter the amount in Litres(or type 'e' to go back)|");
		System.out.println("|                                                   |");
		System.out.println("=====================================================");
		System.out.println(" ");
		System.out.print(BOLD+GREEN+"Please enter the amount: "+RESET+BOLD);
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("e")) {
			enterAmount();
			return;
		}

		try {
			userAmount = Double.parseDouble(input);
			this.cost = fuelDispatcherService.calculateByLiters(userAmount);
			Litres= userAmount;
			confirmTransaction();
		} catch (NumberFormatException e) {
			System.out.println(BOLD+RED+"Invalid input. Please enter a valid number."+RESET+BOLD);
			calculateByLiters();
		}
	}

	private void confirmTransaction() {
		System.out.println(" ");
		System.out.println("====================================================");
		System.out.println("|         TRANSACTION CONFIRMATION                 |");
		System.out.println("====================================================");
		System.out.println("       Pump Number: " + pumpNumber + "              ");
		System.out.println("       Fuel Type: " + selectedFuelType + "          ");
		System.out.println("       Total Amount: " + cost + "             ");
		System.out.println("       Fuel Amount: " + Litres + "             ");
		System.out.println("====================================================");
		System.out.println("|                                                  | ");
		System.out.println("|  1. Confirm Transaction                          |");
		System.out.println("|  2. Go Back to Edit Amount                       |");
		System.out.println("|  3. Cancel Transaction and Return to Main Menu   |");
		System.out.println("|                                                  | ");
		System.out.println("====================================================");
		System.out.println(" ");
		System.out.print(BOLD+GREEN+"Enter your choice: "+RESET+BOLD);
		String choice = scanner.nextLine();
		switch (choice) {
		case "1":
			fuelDispatcherService.confirmTransaction();
			break;
		case "2":
			enterAmount();
			break;
		case "3":
			fuelDispatcherService.cancelOperation();
			break;
		default:
			System.out.println(BOLD+RED+"Invalid choice. Please select a valid option."+RESET+BOLD);
			confirmTransaction();
		}
	}
}