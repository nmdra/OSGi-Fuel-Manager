package com.fuel.mainclient;

import com.fuel.clientservice.ClientActivator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import java.util.Scanner;

public class MainClientActivator implements BundleActivator {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void start(BundleContext context) throws Exception {
        // Display ASCII Art Banner
        System.out.println("\n");
        System.out.println(" ███████╗██╗   ██╗███████╗██╗     ");
        System.out.println(" ██╔════╝██║   ██║██╔════╝██║     ");
        System.out.println(" █████╗  ██║   ██║█████╗  ██║     ");
        System.out.println(" ██╔══╝  ██║   ██║██╔══╝  ██║     ");
        System.out.println(" ██║     ╚██████╔╝███████╗███████╗");
        System.out.println(" ╚═╝      ╚═════╝ ╚══════╝╚══════╝");
        System.out.println("🚀 Welcome to Fuel Management System 🚀\n");

        while (true) {
            System.out.println("👤 Select Your Role:");
            System.out.println("1️⃣  Manager 🏢");
            System.out.println("2️⃣  Exit 🚪");

            System.out.print("👉 Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("🔑 Accessing Manager Dashboard...");
                    startClientService(context);
                    break;
                case 2:
                    System.out.println("👋 Exiting Fuel Management System. Have a great day!");
                    return; // Exit loop and stop execution
                default:
                    System.out.println("⚠️ Invalid choice! Please enter 1 or 2.");
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
}

