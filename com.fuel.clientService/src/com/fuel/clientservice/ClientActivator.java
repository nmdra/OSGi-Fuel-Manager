package com.fuel.clientservice;

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
        System.out.print("🔧 Enter Fuel Type: ");
        String type = sc.nextLine();
        System.out.print("💵 Enter Fuel Price: ");
        double price = sc.nextDouble();
        System.out.print("⛽ Enter Fuel Quantity: ");
        double quantity = sc.nextDouble();
        fuelService.addFuelType(type, price, quantity);
        System.out.println("✅ Fuel Type added successfully.");
    }

    // Update Fuel Level
    private void updateFuelLevel() {
        System.out.print("🔧 Enter Fuel Type: ");
        String type = sc.nextLine();
        System.out.print("⛽ Enter Fuel Quantity to Update: ");
        double quantity = sc.nextDouble();
        fuelService.updateFuelLevel(type, quantity);
        System.out.println("✅ Fuel Level updated successfully.");
    }

    // Check Fuel Level
    private void checkFuelLevel() {
        System.out.print("🔍 Enter Fuel Type: ");
        String type = sc.nextLine();
        double level = fuelService.checkFuelLevel(type);
        System.out.println("⚡ Fuel Level for " + type + ": " + level);
    }

    // Order Fuel Truck
    private void orderFuelTruck() {
        System.out.print("🚚 Enter Fuel Type: ");
        String type = sc.nextLine();
        System.out.print("🛢️ Enter Fuel Quantity to Order: ");
        double quantity = sc.nextDouble();
        fuelService.orderFuelTruck(type, quantity);
        System.out.println("✅ Fuel Truck ordered successfully.");
    }

    // Reduce Fuel Quantity
    private void reduceFuelQuantity() {
        System.out.print("🔧 Enter Fuel Type: ");
        String type = sc.nextLine();
        System.out.print("🔽 Enter Fuel Quantity to Reduce: ");
        double quantity = sc.nextDouble();
        fuelService.reduceFuelQuantity(type, quantity);
        System.out.println("⚡ Fuel Quantity reduced successfully.");
    }

    // Notification menu
    private void notificationMenu() {
        System.out.println("\n--- 📲 Notifications ---");
        System.out.println("1. View Notifications");
        System.out.println("2. Mark Notification as Read");
        System.out.println("3. Back to Main Menu");

        System.out.print("👉 Choose an option: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewNotifications();
                break;
            case 2:
                markNotificationAsRead();
                break;
            case 3:
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
        }
    }

    // Mark Notification as Read
    private void markNotificationAsRead() {
        System.out.print("✔️ Enter notification index to mark as read: ");
        int index = sc.nextInt();
        notificationService.markNotificationAsRead(index);
        System.out.println("✅ Notification marked as read.");
    }
}




