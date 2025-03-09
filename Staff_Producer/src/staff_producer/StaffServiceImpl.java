package staff_producer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class StaffServiceImpl implements StaffService {
	private static final String FILE_NAME = ".\\Staff_info.txt";
    private Scanner scan;
    private String ANSI_BOLD = "\u001B[1m";
    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_YELLOW = "\u001B[33m";
    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RED = "\u001B[31m";
    
    private List<Staff> registeredStaffs;

    public StaffServiceImpl() {
    	scan = new Scanner(System.in);
    	registeredStaffs = new ArrayList<>();
    }

    @Override
    public void createStaff() {
        String StaffID, name, email, contact;

        System.out.println("\n \u001B[33m \u001B[1mRegister New Staff To System\u001B[0m");

        System.out.print("\n \u001B[1m Staff's ID: \u001B[0m");
        StaffID = scan.nextLine();

        System.out.print(" \u001B[1m Staff's Name: \u001B[0m");
        name = scan.nextLine();

        System.out.print(" \u001B[1m Staff's Email: \u001B[0m");
        email = scan.nextLine();

        System.out.print(" \u001B[1m Staff's Contact Number: \u001B[0m");
        contact = scan.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(StaffID + "," + name + "," + email + "," + contact + "\n");

            System.out.println("\n \u001B[32m Staff with ID " + StaffID + " registered successfully.\u001B[0m");
        } catch (IOException e) {
            System.err.println("\n \u001B[31m Error registering new Staff to system: " + e.getMessage() + "\u001B[0m");
        }
    }

    
    
    @Override
    public void displayStaffs() {
        // Define the content width and the title
        int contentWidth = 80; // Adjust as needed
        String title = "  Staffs' Details";

        // Calculate the padding for center alignment
        int titlePadding = (contentWidth - title.length()) / 2;
        
        System.out.println();
        System.out.print(ANSI_BOLD + ANSI_YELLOW);
        for (int i = 0; i < titlePadding; i++) {
            System.out.print(" ");
        }
        System.out.print(title);
        System.out.println(ANSI_RESET);

        String lineSeparator = "  +------------+----------------------+---------------------------+-----------------+%n";
        String widthFormat = "  | %-10s | %-20s | %-25s | %-15s |%n";

        System.out.printf(lineSeparator);
        System.out.printf(widthFormat, "StaffID", "Name", "Email", "Contact No");
        System.out.printf(lineSeparator);

        List<Staff> StaffList = getAllStaffs();

        for (Staff Staff : StaffList) {
            String formattedString = String.format(widthFormat, Staff.getStaffID(), Staff.getName(), Staff.getEmail(), Staff.getContact());
            System.out.printf(formattedString);
        }

        System.out.printf(lineSeparator);
    }



    @Override
    public void editStaff() {
        String StaffID, newName, newContact, newEmail;
        
        System.out.println("\n \u001B[33m \u001B[1mEdit Staff Profile\u001B[0m \n");
       
        System.out.print(ANSI_BOLD +"  Enter Staff's ID: " + ANSI_RESET);
        StaffID = scan.nextLine();

        Staff StaffToEdit = findStaffById(StaffID);
        if (StaffToEdit != null) {
            // Display existing details
            System.out.println("\n  Current Details:");
            System.out.println(ANSI_BOLD + "  1. Name" + ANSI_RESET + "  - " + StaffToEdit.getName());
            System.out.println(ANSI_BOLD + "  2. Email" + ANSI_RESET + " - " + StaffToEdit.getEmail());
            System.out.println(ANSI_BOLD + "  3. Contact" + ANSI_RESET + " - " + StaffToEdit.getContact());

            // Prompt user for changes
            System.out.print("\n" + ANSI_BLUE + "  Enter the indexes of the properties you want to edit (comma-separated): " + ANSI_RESET);
            String indexesInput = scan.nextLine();
            String[] indexes = indexesInput.split(",");

            // Edit the chosen properties
            for (String index : indexes) {
                int propertyIndex = Integer.parseInt(index.trim());
                switch (propertyIndex) {
                    case 1:
                        System.out.print(ANSI_BOLD + "  Enter new name" + ANSI_RESET + ": ");
                        newName = scan.nextLine();
                        StaffToEdit.setName(newName);
                        break;
                    case 2:
                        System.out.print(ANSI_BOLD + "  Enter new email" + ANSI_RESET + ": ");
                        newEmail = scan.nextLine();
                        StaffToEdit.setEmail(newEmail);
                        break;
                    case 3:
                        System.out.print(ANSI_BOLD + "  Enter new contact" + ANSI_RESET + ": ");
                        newContact = scan.nextLine();
                        StaffToEdit.setContact(newContact);
                        break;
                    default:
                        System.out.println("  Invalid index: " + propertyIndex);
                }
            }
            
            // Update the file with the edited details
            updateStaffInFile(StaffToEdit);
            System.out.println();
            System.out.println(ANSI_GREEN +"  Staff details updated for ID " + StaffID + ":" + ANSI_RESET);
            System.out.println("  Name: " + StaffToEdit.getName());
            System.out.println("  Email: " + StaffToEdit.getEmail());
            System.out.println("  Contact: " + StaffToEdit.getContact());
        } else {
        	 System.err.println("\n \u001B[31m Staff not found with ID: " + StaffID + "\u001B[0m");
        }
    }

   
    
    @Override
    public void deleteStaff(String StaffID) {
        try {
            List<Staff> StaffListToDelete = getAllStaffs();

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false));

            for (Staff Staff : StaffListToDelete) {
                if (!Staff.getStaffID().equals(StaffID)) {
                    writer.write(Staff.getStaffID() + "," + Staff.getName() + "," + Staff.getEmail() + "," + Staff.getContact() + "\n");
                }
            }

            writer.close();
            System.out.println("\n \u001B[32m Staff with ID " + StaffID + " Deleted Successfully.\u001B[0m");
        } catch (IOException e) {
        	 System.err.println("\n \u001B[31m Error deleting Staff: " + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    @Override
    public List<Staff> getAllStaffs() {
        List<Staff> StaffList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_NAME)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String StaffID = parts[0];
                String name = parts[1];
                String email = parts[2];
                String contact = parts[3];
                Staff Staff = new Staff(StaffID, name, email, contact);
                StaffList.add(Staff);
            }
        } catch (IOException e) {
            System.err.println("Error reading Staff data from file: " + e.getMessage());
        }
        return StaffList;
    }
    
    @Override
    public void assignCourses(String StaffID) {
        Staff Staff = findStaffById(StaffID);
        if (Staff != null) {
                System.out.print("\n" + ANSI_BLUE + "  Enter Shifts to assign (comma-separated): " + ANSI_RESET);
                String ShiftsInput = scan.nextLine();
                List<String> Shifts = Arrays.asList(ShiftsInput.split("\\s*,\\s*"));

                // Update the assigned Shifts for the Staff
                List<String> currentShifts = Staff.getAssignedCourses();
                currentShifts.addAll(Shifts);
                Staff.setAssignedCourses(currentShifts);

                // Update the file with the assigned Shifts
                updateStaffInFile(Staff);

                System.out.println(ANSI_GREEN + "  Shifts assigned successfully." + ANSI_RESET);
            
        } else {
            System.err.println("\n" + ANSI_RED + "  Staff not found with ID: " + StaffID + ANSI_RESET);
        }
    }

   
    private Staff findStaffById(String StaffID) {
        List<Staff> StaffList = getAllStaffs();
        for (Staff Staff : StaffList) {
            if (Staff.getStaffID().equals(StaffID)) {
                return Staff;
            }
        }
        return null;
    }
    
    private void updateStaffInFile(Staff updatedStaff) {
        List<Staff> StaffList = getAllStaffs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_NAME), false))) {
            for (Staff Staff : StaffList) {
                if (Staff.getStaffID().equals(updatedStaff.getStaffID())) {
                    // Write the updated record with assigned Shifts
                    writer.write(updatedStaff.getStaffID() + "," +
                            updatedStaff.getName() + "," +
                            updatedStaff.getEmail() + "," +
                            updatedStaff.getContact() + "," +
                            String.join(",", updatedStaff.getAssignedCourses()) + "\n");
                } else {
                    // Write the unchanged record
                    writer.write(Staff.getStaffID() + "," +
                            Staff.getName() + "," +
                            Staff.getEmail() + "," +
                            Staff.getContact() + "," +
                            String.join(",", Staff.getAssignedCourses()) + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating Staff data in file: " + e.getMessage());
        }
    }

	@Override
	public void searchStaff(String StaffID) {
        Staff Staff = findStaffById(StaffID);
        if (Staff != null) {
        	System.out.println("\n  Staff Details:");
            System.out.println(ANSI_BOLD + "  Staff ID" + ANSI_RESET + "    - " + Staff.getStaffID());
            System.out.println(ANSI_BOLD + "  Name" + ANSI_RESET + "          - " + Staff.getName());
            System.out.println(ANSI_BOLD + "  Email" + ANSI_RESET + "         - " + Staff.getEmail());
            System.out.println(ANSI_BOLD + "  Contact" + ANSI_RESET + "       - " + Staff.getContact());

            List<String> assignedShifts = Staff.getAssignedCourses();
            if (!assignedShifts.isEmpty()) {
                System.out.println(ANSI_BOLD + "  Assigned Shifts" + ANSI_RESET + " - " + String.join(", ", assignedShifts));
            }
        } else {
            System.err.println("\n \u001B[31m Staff not found with ID: " + StaffID + "\u001B[0m");
        }
    }
    

}