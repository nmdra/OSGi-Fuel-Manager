package com.fuel.mainclient;

import com.fuel.DispatcherClient.FuelDispatcherClientActivator;
import com.fuel.clientservice.ClientActivator;
import staff_consumer.Staff_Consumer_Activator;
import com.fuelstation.emergencypublisher.EmergencyPublisherActivator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import java.util.Scanner;

public class MainClientActivator implements BundleActivator {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void start(BundleContext context) throws Exception {
        // ANSI color codes
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";
        final String YELLOW = "\u001B[33m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";
        final String BOLD = "\u001B[1m";

        // Display ASCII Art Banner with colors
        System.out.println("\n" + BOLD + CYAN +
            " ███████╗██╗   ██╗███████╗██╗     \n" +
            " ██╔════╝██║   ██║██╔════╝██║     \n" +
            " █████╗  ██║   ██║█████╗  ██║     \n" +
            " ██╔══╝  ██║   ██║██╔══╝  ██║     \n" +
            " ██║     ╚██████╔╝███████╗███████╗\n" +
            " ╚═╝      ╚═════╝ ╚══════╝╚══════╝\n" +
            "🚀 Welcome to Fuel Station Management System 🚀\n" + RESET);

        while (true) {
            System.out.println(BOLD + GREEN + "👤 Select Dashboard:" + RESET);
            System.out.println(YELLOW + "1. Manage Fuel and Notifications 🏢" + RESET);
            System.out.println(BLUE + "2. Fuel Dispatcher ⛽" + RESET);
            System.out.println(GREEN + "3. Manage Staff 👥" + RESET);
            System.out.println(RED + "4. Emergency 🚨" + RESET);
            System.out.println(CYAN + "5. Exit 🚪" + RESET);
            
            System.out.print("👉 " + BOLD + "Enter your choice: " + RESET);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println(GREEN + "🔑 Accessing Fuel Management Dashboard..." + RESET);
                    startClientService(context);
                    break;
                    
                case 2:
                    System.out.println(BLUE + "🔑 Accessing Fuel Dispatcher Dashboard..." + RESET);
                    startDispatcherClient(context);
                    break;

                    
                case 3:
                	System.out.println(BLUE + "🔑 Accessing Staff Management Dashboard..." + RESET);
                	startStaffManagementClient(context);
                	break;
             
                case 4:
                    System.out.println(RED + "🔑 Accessing Emergency Dashboard..." + RESET);
                    startEmergencyClient(context);
                    break;

                case 5:
                    System.out.println(YELLOW + "👋 Shutting down Fuel Management System..." + RESET);

                    // Stop OSGi bundles and services
                    try {
                        if (context != null) {
                            context.getBundle(0).stop(); // Stops the entire OSGi framework
                        }
                    } catch (Exception e) {
                        System.out.println(RED + "⚠️ Error while stopping services: " + e.getMessage() + RESET);
                    }

                    System.out.println(GREEN + "✅ All services stopped. Exiting.." + RESET);
                    System.exit(0); // Terminates the JVM
                    break;

                default:
                    System.out.println(RED + "⚠️ Invalid choice! Please enter 1, 2, 3, 4 or 5." + RESET);
            }
        }
    }

    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("📴 Fuel Management System Stopping...");
        scanner.close();
    }

    // Starts the Fuel Client Service (Manager Dashboard)
    private void startClientService(BundleContext context) {
        try {
            ClientActivator managerClient = new ClientActivator();
            managerClient.start(context); // Start Client Service via OSGi
        } catch (Exception e) {
            System.out.println("❌ Error initializing Client Service: " + e.getMessage());
        }
    }
	
	// Starts the Fuel Client Service (Manager Dashboard)
    private void startDispatcherClient(BundleContext context) {
        try {
            FuelDispatcherClientActivator fuelClient = new FuelDispatcherClientActivator();
            fuelClient.start(context); // Start Client Service via OSGi
        } catch (Exception e) {
            System.out.println("❌ Error initializing Client Service: " + e.getMessage());
        }
    }
    
    private void startEmergencyClient(BundleContext context) {
        try {
            EmergencyPublisherActivator emergencyClient = new EmergencyPublisherActivator();
            emergencyClient.start(context); // Start Client Service via OSGi
        } catch (Exception e) {
            System.out.println("❌ Error initializing Client Service: " + e.getMessage());
        }
    }
    
    // Start Staff Management Dashboard
    private void startStaffManagementClient(BundleContext context) {
        try {
            Staff_Consumer_Activator staffConsumerClient = new Staff_Consumer_Activator();
            staffConsumerClient.start(context); // Start Client Service via OSGi
        } catch (Exception e) {
            System.out.println("❌ Error initializing Client Service: " + e.getMessage());
        }
    }
	
}

