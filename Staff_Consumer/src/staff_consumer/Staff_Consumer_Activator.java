package staff_consumer;

import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import staff_producer.StaffService;

public class Staff_Consumer_Activator implements BundleActivator {

    private ServiceReference serviceReference;
    private Scanner scanner;
    private final String BOLD_TEXT = "\u001B[1m";
    private final String RESET_TEXT = "\u001B[0m";
    private final String YELLOW_TEXT = "\u001B[33m";
    private final String GREEN_TEXT = "\u001B[32m";
    private final String PURPLE_BOLD = "\u001B[1;35m";

    public void start(BundleContext context) throws Exception {
        System.out.println("Staff Consumer Started");
        serviceReference = context.getServiceReference(StaffService.class.getName());
        
        @SuppressWarnings("unchecked")
        StaffService staffService = (StaffService) context.getService(serviceReference);
        scanner = new Scanner(System.in);

        String title = "STAFF MANAGEMENT - Ceypecto Fuel Station, Malabe";
        int contentWidth = 50;
        int padding = (contentWidth - title.length()) / 2;
        String formattedTitle = PURPLE_BOLD + " ".repeat(padding) + title + " ".repeat(padding) + RESET_TEXT;
        System.out.println("\n" + formattedTitle);

        while (true) {
            System.out.println("\n" + GREEN_TEXT + BOLD_TEXT + "╔════════════════════════════════════════════════╗" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║       Select an Option - Staff Management      ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "╠════╦═══════════════════════════════════════════╣" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  1 ║  Add a New Staff                          ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  2 ║  View Staff List                          ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  3 ║  Modify Staff Information                 ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  4 ║  Remove Staff                             ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  5 ║  Allocate Shifts                          ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  6 ║  Find Staff                               ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "║  7 ║  Exit                                     ║" + RESET_TEXT);
            System.out.println(GREEN_TEXT + BOLD_TEXT + "╚════╩═══════════════════════════════════════════╝" + RESET_TEXT);

            System.out.print("\n " + BOLD_TEXT + "Enter your choice: " + RESET_TEXT);
            int selection = scanner.nextInt();
            scanner.nextLine();
            System.out.println("  --------------------------------------------------------------------");

            switch (selection) {
                case 1:
                    staffService.createStaff();
                    break;
                case 2:
                    staffService.displayStaffs();
                    break;
                case 3:
                    staffService.editStaff();
                    break;
                case 4:
                    System.out.println("\n " + YELLOW_TEXT + BOLD_TEXT + "Remove Staff" + RESET_TEXT);
                    System.out.print("\n " + GREEN_TEXT + "Enter Staff ID to delete: " + RESET_TEXT);
                    String staffId = scanner.nextLine();
                    staffService.deleteStaff(staffId);
                    break;
                case 5:
                    System.out.println("\n " + YELLOW_TEXT + BOLD_TEXT + "Assign Shift to Staff" + RESET_TEXT);
                    System.out.print("\n " + BOLD_TEXT + "Enter Staff ID: " + RESET_TEXT);
                    String shiftId = scanner.nextLine();
                    staffService.assignCourses(shiftId);
                    break;
                case 6:
                    System.out.println("\n " + YELLOW_TEXT + BOLD_TEXT + "Search Staff" + RESET_TEXT);
                    System.out.print("\n " + GREEN_TEXT + "Enter Staff ID to search: " + RESET_TEXT);
                    String searchId = scanner.nextLine();
                    staffService.searchStaff(searchId);
                    break;
                case 7:
                    System.out.println("\n " + GREEN_TEXT + "Exiting..." + RESET_TEXT);
                    return;
                default:
                    System.out.println("\n " + "\u001B[31m Invalid option! Please try again.\u001B[0m");
            }
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Staff Consumer Stopped");
        context.ungetService(serviceReference);
    }
}
