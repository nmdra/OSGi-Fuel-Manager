package com.fuel.clientservice;

import com.fuel.fuelservice.FuelType;
import com.fuel.fuelservice.IFuelService;
import com.fuel.notificationsservice.INotificationService;
import com.fuel.notificationsservice.NotificationModel;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ClientActivator implements BundleActivator {

    private IFuelService fuelService;
    private INotificationService notificationService;
    private Scanner sc = new Scanner(System.in);

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("🚀 Client Service Starting...");

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
        System.out.println("🛑 Client Service Shutting Down...");
    }

    // Display the menu for interacting with the services
    private void displayMenu() {
        while (true) {
            System.out.println("\n--- 💼 Client Service ---");
            System.out.println("1. Fuel Management");
            System.out.println("2. View Notifications");
            System.out.println("3. Exit");

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
                    System.out.println("👋 Exiting Client Service...");
                    return;
                default:
                    System.out.println("❌ Invalid choice, try again.");
            }
        }
    }

    // Fuel Management menu
    private void fuelManagementMenu() {
        System.out.println("\n--- ⛽ Fuel Management ---");
        System.out.println("1. Add Fuel Type");
        System.out.println("2. Update Fuel Level");
        System.out.println("3. Check Fuel Level");
        System.out.println("4. Order Fuel Truck");
        System.out.println("5. Reduce Fuel Quantity");
        System.out.println("6. Back to Main Menu");

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
                return;
            default:
                System.out.println("❌ Invalid choice, try again.");
        }
    }

    // Add Fuel Type
    private void addFuelType() {
        FuelType selectedFuelType = null;
        
        // Loop until a valid fuel type is entered
        while (selectedFuelType == null) {
            System.out.println("🔧 Available Fuel Types:");
            for (FuelType fuelType : FuelType.values()) {
                System.out.println("   ➡️ " + fuelType);
            }

            System.out.print("👉 Select Fuel Type from the list: ");
            String typeInput = sc.nextLine().toUpperCase();

            // Validate the selected fuel type
            try {
                selectedFuelType = FuelType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid fuel type! Please select a valid type from the list.");
            }
        }
        
        // Proceed to get fuel price and quantity
        double price = getDoubleInput("💵 Enter Fuel Price: ");
        double quantity = getDoubleInput("⛽ Enter Fuel Quantity: ");

        // Add fuel type
        fuelService.addFuelType(selectedFuelType.name(), price, quantity);
        System.out.println("✅ Fuel Type " + selectedFuelType + " added successfully.");
    }

    // Update Fuel Level with validation
    private void updateFuelLevel() {

        FuelType selectedFuelType = null;
        
        // Loop until a valid fuel type is entered
        while (selectedFuelType == null) {
            System.out.println("🔧 Available Fuel Types:");
            for (FuelType fuelType : FuelType.values()) {
                System.out.println("   ➡️ " + fuelType);
            }

            System.out.print("👉 Select Fuel Type from the list: ");
            String typeInput = sc.nextLine().toUpperCase();

            // Validate the selected fuel type
            try {
                selectedFuelType = FuelType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid fuel type! Please select a valid type from the list.");
            }
        }
    	
        double quantity = getDoubleInput("⛽ Enter Fuel Quantity to Update: ");
        fuelService.updateFuelLevel(selectedFuelType.name(), quantity);
        System.out.println("✅ Fuel Level updated successfully.");
    }

    // Check Fuel Level
    private void checkFuelLevel() {
        FuelType selectedFuelType = null;
        
        // Loop until a valid fuel type is entered
        while (selectedFuelType == null) {
            System.out.println("🔧 Available Fuel Types:");
            for (FuelType fuelType : FuelType.values()) {
                System.out.println("   ➡️ " + fuelType);
            }

            System.out.print("👉 Select Fuel Type from the list: ");
            String typeInput = sc.nextLine().toUpperCase();

            // Validate the selected fuel type
            try {
                selectedFuelType = FuelType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid fuel type! Please select a valid type from the list.");
            }
        }
    	
        double level = fuelService.checkFuelLevel(selectedFuelType.name());
        System.out.println("⚡ Fuel Level for " + selectedFuelType.name() + ": " + level);
    }

    // Order Fuel Truck
    private void orderFuelTruck() {
        FuelType selectedFuelType = null;
        
        // Loop until a valid fuel type is entered
        while (selectedFuelType == null) {
            System.out.println("🔧 Available Fuel Types:");
            for (FuelType fuelType : FuelType.values()) {
                System.out.println("   ➡️ " + fuelType);
            }

            System.out.print("👉 Select Fuel Type from the list: ");
            String typeInput = sc.nextLine().toUpperCase();

            // Validate the selected fuel type
            try {
                selectedFuelType = FuelType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid fuel type! Please select a valid type from the list.");
            }
        }
    	
        System.out.print("🛢️ Enter Fuel Quantity to Order: ");
        double quantity = sc.nextDouble();
        fuelService.orderFuelTruck(selectedFuelType.name(), quantity);
        System.out.println("✅ Fuel Truck ordered successfully.");
    }

    // Reduce Fuel Quantity
    private void reduceFuelQuantity() {
        FuelType selectedFuelType = null;
        
        // Loop until a valid fuel type is entered
        while (selectedFuelType == null) {
            System.out.println("🔧 Available Fuel Types:");
            for (FuelType fuelType : FuelType.values()) {
                System.out.println("   ➡️ " + fuelType);
            }

            System.out.print("👉 Select Fuel Type from the list: ");
            String typeInput = sc.nextLine().toUpperCase();

            // Validate the selected fuel type
            try {
                selectedFuelType = FuelType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid fuel type! Please select a valid type from the list.");
            }
        }
    	
        System.out.print("🔽 Enter Fuel Quantity to Reduce: ");
        double quantity = sc.nextDouble();
        fuelService.reduceFuelQuantity(selectedFuelType.name(), quantity);
        System.out.println("⚡ Fuel Quantity reduced successfully.");
    }

    // Notification menu
    private void notificationMenu() {
        System.out.println("\n--- 📲 Notifications ---");
        System.out.println("1. View Notifications");
        System.out.println("2. Back to Main Menu");

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
                System.out.println("❌ Invalid choice, try again.");
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

    
 // Method to get valid integer input with error handling
    private int getIntInput(String prompt) {
        int choice = -1;
        while (choice < 0) {
            System.out.print(prompt);
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
                if (choice < 0) {
                    System.out.println("❌ Invalid input! Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
                sc.nextLine(); // Clear the invalid input
            }
        }
        return choice;
    }

    // Method to get valid double input with error handling
    private double getDoubleInput(String prompt) {
        double value = -1.0;
        while (value < 0) {
            System.out.print(prompt);
            try {
                value = sc.nextDouble();
                sc.nextLine(); // Consume newline
                if (value < 0) {
                    System.out.println("❌ Invalid input! Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
                sc.nextLine(); // Clear the invalid input
            }
        }
        return value;
    }
}




