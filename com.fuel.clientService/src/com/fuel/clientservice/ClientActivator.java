package com.fuel.clientservice;

import com.fuel.authpublisher.IAuthService;
import com.fuel.fuelservice.FuelType;
import com.fuel.fuelservice.IFuelService;
import com.fuel.notificationsservice.INotificationService;
import com.fuel.notificationsservice.NotificationModel;

import java.util.List;
import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ClientActivator implements BundleActivator {

	private IFuelService fuelService;
	private INotificationService notificationService;
	private Scanner sc = new Scanner(System.in);
	
	// ANSI color codes
	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[31m";
	private static final String YELLOW = "\u001B[33m";
	private static final String CYAN = "\u001B[36m";
	private static final String MAGENTA = "\u001B[35m";
	private static final String WHITE = "\u001B[37m";

	@Override
	public void start(BundleContext context) throws Exception {

	    // Retrieve Authentication Service Reference
	    ServiceReference<IAuthService> authServiceReference = context.getServiceReference(IAuthService.class);
	    if (authServiceReference != null) {
	        IAuthService authService = context.getService(authServiceReference);

	        if (authService != null) {
	            Scanner scanner = new Scanner(System.in);

	            System.out.print("👤 Enter Username: ");
	            String username = scanner.nextLine();

	            System.out.print("🔑 Enter Password: ");
	            String password = scanner.nextLine();

	            if (!authService.login(username, password)) {
	                System.out.println("🚫 Access denied! Exiting...");
	                return; // Exit if authentication fails
	            }
	        }
	    } else {
	        System.out.println("⚠️ Authentication Service not available. Exiting...");
	        return; // Exit if authentication service is unavailable
	    }

	    // Retrieve Fuel Service Reference
	    ServiceReference<IFuelService> fuelServiceReference = context.getServiceReference(IFuelService.class);
	    if (fuelServiceReference != null) {
	        fuelService = context.getService(fuelServiceReference);
	        System.out.println("💥 Fuel Service available.");
	    } else {
	        System.out.println("⚠️ Fuel Service not available.");
	    }

	    // Retrieve Notification Service Reference
	    ServiceReference<INotificationService> notificationServiceReference = context.getServiceReference(INotificationService.class);
	    if (notificationServiceReference != null) {
	        notificationService = context.getService(notificationServiceReference);
	        System.out.println("🔔 Notification Service available.");
	    } else {
	        System.out.println("⚠️ Notification Service not available.");
	    }

	    // Show menu and interact with services
	    displayMenu();
	}


	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("🛑 Manager Service Shutting Down...");
	}

	// Display the menu for interacting with the services
	private void displayMenu() {
		while (true) {
			System.out.println(CYAN + "\n╔══════════════════════════════╗");
			System.out.println("║      💼 Client Service       ║");
			System.out.println("╠══════════════════════════════╣");
			System.out.println("║ 1. Fuel Management           ║");
			System.out.println("║ 2. View Notifications        ║");
			System.out.println("║ 3. Exit                      ║");
			System.out.println("╚══════════════════════════════╝" + RESET);

			System.out.print("👉 Choose an option: ");
			int choice = sc.nextInt();
			sc.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				fuelManagementMenu();
				break;
			case 2:
				notificationMenu();
				break;
			case 3:
				System.out.println(YELLOW + "👋 Exiting Client Service..." + RESET);
				return;
			default:
				System.out.println(RED + "❌ Invalid choice, try again." + RESET);
			}
		}
	}

	// Fuel Management menu
	private void fuelManagementMenu() {
		System.out.println(MAGENTA + "\n╔══════════════════════════════╗");
		System.out.println("║      ⛽ Fuel Management      ║");
		System.out.println("╠══════════════════════════════╣");
		System.out.println("║ 1. Add Fuel Type             ║");
		System.out.println("║ 2. Update Fuel Level         ║");
		System.out.println("║ 3. Check Fuel Level          ║");
		System.out.println("║ 4. Order Fuel Truck          ║");
		System.out.println("║ 5. Reduce Fuel Quantity      ║");
		System.out.println("║ 6. Get Fuel Price            ║");
		System.out.println("║ 7. Update Fuel Prices        ║");
		System.out.println("║ 8. Back to Main Menu         ║");
		System.out.println("╚══════════════════════════════╝" + RESET);

		System.out.print("👉 Choose an option: ");
		int choice = sc.nextInt();
		sc.nextLine(); // Consume newline

		switch (choice) {
		case 1:
			addFuelType();
			break;
		case 2:
			updateFuelLevel();
			break;
		case 3:
			checkFuelLevel();
			break;
		case 4:
			orderFuelTruck();
			break;
		case 5:
			reduceFuelQuantity();
			break;
		case 6:
			getFuelPrice();
			break;
		case 7:
			updateFuelPrice();
			break;
		case 8:
			return;
		default:
			System.out.println(RED + "❌ Invalid choice, try again." + RESET);
		}
	}

	private void addFuelType() {
		FuelType selectedFuelType = selectFuelType();

		// Get fuel price and quantity
		System.out.print("💵 Enter Fuel Price: ");
		while (!sc.hasNextDouble()) {
			System.out.println("❌ Invalid input! Please enter a valid price.");
			sc.nextLine();
		}
		double price = sc.nextDouble();

		System.out.print("⛽ Enter Fuel Quantity: ");
		while (!sc.hasNextDouble()) {
			System.out.println("❌ Invalid input! Please enter a valid quantity.");
			sc.nextLine();
		}
		double quantity = sc.nextDouble();
		sc.nextLine(); // Consume newline

		// Add fuel type
		fuelService.addFuelType(selectedFuelType.name(), price, quantity);
		System.out.println("✅ Fuel Type " + selectedFuelType + " added successfully.");
	}

// Update Fuel Level with validation
	private void updateFuelLevel() {
		FuelType selectedFuelType = selectFuelType();

		double quantity = getValidatedQuantity();

		// Update fuel level
		fuelService.updateFuelLevel(selectedFuelType.name(), quantity);
		System.out.println("✅ Fuel Level updated successfully.");
	}

	// Method to validate and retrieve a valid fuel quantity
	private double getValidatedQuantity() {
		double quantity;
		while (true) {
			System.out.print("⛽ Enter Fuel Quantity (Use negative to reduce fuel): ");

			if (sc.hasNextDouble()) {
				quantity = sc.nextDouble();
				sc.nextLine(); // Consume newline
				break; // Valid input, exit loop
			} else {
				System.out.println("❌ Invalid input! Please enter a valid number.");
				sc.nextLine(); // Consume invalid input
			}
		}
		return quantity;
	}

	// Check Fuel Level
	private void checkFuelLevel() {
		FuelType selectedFuelType = selectFuelType();

		double level = fuelService.checkFuelLevel(selectedFuelType.name());
		if (level != -1.0) {
			System.out.println("⚡ Fuel Level for " + selectedFuelType.name() + ": " + level);
		} else {
			System.out.println("This Fuel Type is Empty...");
		}

	}

	// Order Fuel Truck
	private void orderFuelTruck() {
		FuelType selectedFuelType = selectFuelType();

		System.out.print("🛢️ Enter Fuel Quantity to Order: ");
		double quantity = sc.nextDouble();
		fuelService.orderFuelTruck(selectedFuelType.name(), quantity);
		System.out.println("✅ Fuel Truck ordered successfully.");
	}

	// Reduce Fuel Quantity
	private void reduceFuelQuantity() {
		FuelType selectedFuelType = selectFuelType();

		System.out.print("🔽 Enter Fuel Quantity to Reduce: ");
		double quantity = sc.nextDouble();
		fuelService.reduceFuelQuantity(selectedFuelType.name(), quantity);
		System.out.println("⚡ Fuel Quantity reduced successfully.");
	}

	// Notification menu
	private void notificationMenu() {
		System.out.println(WHITE + "\n╔══════════════════════════════╗");
		System.out.println("║        📲 Notifications      ║");
		System.out.println("╠══════════════════════════════╣");
		System.out.println("║ 1. View Notifications        ║");
		System.out.println("║ 2. Back to Main Menu         ║");
		System.out.println("╚══════════════════════════════╝" + RESET);

		System.out.print("👉 Choose an option: ");
		int choice = sc.nextInt();
		sc.nextLine(); // Consume newline

		switch (choice) {
		case 1:
			viewNotifications();
			break;
		case 2:
			return;
		default:
			System.out.println(RED + "❌ Invalid choice, try again." + RESET);
		}
	}
	
	// View Notifications
	private void viewNotifications() {
		List<NotificationModel> notifications = notificationService.getNotifications();
		if (notifications.isEmpty()) {
			System.out.println("📜 No new notifications.");
		} else {
			for (int i = 0; i < notifications.size(); i++) {
				System.out.println(i + ": " + notifications.get(i));
			}

			// Prompt user to mark notifications as read
			markMultipleNotificationsAsRead();
		}
	}

	// Mark multiple Notifications as Read
	private void markMultipleNotificationsAsRead() {
		System.out.print("✔️ Enter notification indices (separate multiple indices with spaces): ");
		String input = sc.nextLine();

		// Split input by spaces and convert to integers
		String[] indices = input.split("\\s+");

		// Iterate through the indices and mark each notification as read
		for (String indexStr : indices) {
			try {
				int index = Integer.parseInt(indexStr);
				notificationService.markNotificationAsRead(index);
				System.out.println("✅ Notification " + index + " marked as read.");
			} catch (NumberFormatException e) {
				System.out.println("❌ Invalid input! Please enter valid indices.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("❌ Invalid index! No notification found at index " + indexStr);
			}
		}
	}

	// Get Fuel Price
	private void getFuelPrice() {
		FuelType selectedFuelType = selectFuelType();
		if (selectedFuelType != null) {
			double price = fuelService.getFuelPrice(selectedFuelType.name());
			if (price != -1) {
				System.out.println("💰 Price for " + selectedFuelType + ": $" + price);
			}
		}
	}

	// Update Fuel Price
	private void updateFuelPrice() {
		FuelType selectedFuelType = selectFuelType();
		if (selectedFuelType != null) {
			double newPrice = getValidatedPrice();
			fuelService.updateFuelPrice(selectedFuelType.name(), newPrice);
		}
	}

	// Select Fuel Type using a number
	private FuelType selectFuelType() {
		FuelType[] fuelTypes = FuelType.values();
		while (true) {
			System.out.println("🔧 Available Fuel Types:");
			for (int i = 0; i < fuelTypes.length; i++) {
				System.out.println("   " + (i + 1) + ". " + fuelTypes[i]);
			}

			System.out.print("👉 Select Fuel Type (Enter Number): ");
			if (sc.hasNextInt()) {
				int typeIndex = sc.nextInt();
				sc.nextLine(); // Consume newline

				if (typeIndex >= 1 && typeIndex <= fuelTypes.length) {
					return fuelTypes[typeIndex - 1];
				} else {
					System.out.println("❌ Invalid selection! Please enter a valid number.");
				}
			} else {
				System.out.println("❌ Invalid input! Please enter a number.");
				sc.nextLine(); // Consume invalid input
			}
		}
	}

	// Validate and get fuel price input
	private double getValidatedPrice() {
		double price;
		while (true) {
			System.out.print("💵 Enter new fuel price: ");
			if (sc.hasNextDouble()) {
				price = sc.nextDouble();
				sc.nextLine(); // Consume newline
				if (price > 0) {
					return price;
				} else {
					System.out.println("❌ Price must be greater than zero.");
				}
			} else {
				System.out.println("❌ Invalid input! Please enter a valid number.");
				sc.nextLine(); // Consume invalid input
			}
		}
	}
}


