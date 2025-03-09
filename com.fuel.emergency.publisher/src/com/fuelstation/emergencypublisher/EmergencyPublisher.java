package com.fuelstation.emergencypublisher;

import com.fuelstation.emergencymodel.EmergencyType;
import com.fuelstation.emergencymodel.FuelStatus;
import com.fuelstation.emergencymodel.EmergencyAlert;

import java.util.Scanner;

public class EmergencyPublisher {

	private static final String RESET = "\u001B[0m"; // Reset color
	private static final String RED = "\u001B[31m"; // Red color
	private static final String GREEN = "\u001B[32m"; // Green color
	private static final String YELLOW = "\u001B[33m"; // Yellow color
	private static final String BLUE = "\u001B[34m"; // Blue color
	private static final String CYAN = "\u001B[36m"; // Cyan color

	private Scanner sc = new Scanner(System.in);

	public void start() {
		displayWelcomeMessage();

		while (true) {
			displayEmergencyMenu();
			Integer  option = getIntInput("Select an option: ", 1, 5);

	        if (option == null) {
	            System.out.println(YELLOW + "Returning to main menu..." + RESET);
	            return; // Exit to main menu
	        }

	        
			EmergencyType emergencyType;
			switch (option) {
			case 1:
				emergencyType = EmergencyType.Fire;
				break;
			case 2:
				emergencyType = EmergencyType.Flood;
				break;
			case 3:
				emergencyType = EmergencyType.Electric_Leak;
				break;
			case 4:
				emergencyType = EmergencyType.Other;
				break;
			case 5:
				return;
			default:
				System.out.println(RED + "Invalid option. Please try again." + RESET);
				continue;
			}

			String location = getStringInput("What is the place? (Pump 1, Pump 2, etc.): ");
	        if (location == null) {
	            System.out.println(YELLOW + "Returning to main menu..." + RESET);
	            return; // Exit to main menu
	        }
	        boolean fuelRemains = getBooleanInput("Fuel remains in shell (true/false): ");
			// boolean critical_level=getBooleanInput()

			FuelStatus fuelStatus = new FuelStatus(!fuelRemains, false); // isTankEmpty = !fuelRemains
			EmergencyAlert alert = new EmergencyAlert(location, emergencyType, fuelStatus);

			handleEmergency(alert);
		}
	}

	void handleEmergency(EmergencyAlert alert) {
		System.out.println(CYAN + "\n=== Emergency Detected ===" + RESET);
		System.out.println(YELLOW + "Location: " + alert.getLocation() + RESET);
		System.out.println(YELLOW + "Type: " + alert.getEmergencyType() + RESET);

		if (alert.getEmergencyType() == EmergencyType.Fire) {
			handleFireEmergency(alert);
		} else if (alert.getEmergencyType() == EmergencyType.Flood) {
			handleFloodEmergency(alert);
		} else if (alert.getEmergencyType() == EmergencyType.Electric_Leak) {
			handleCurruntLeak(alert);
		}
	}

	private void handleFireEmergency(EmergencyAlert alert) {
		System.out.println(RED + "\n=== Fire Emergency Protocol ===" + RESET);

		FuelStatus fuelStatus = alert.getFuelStatus();
		if (fuelStatus.isTankEmpty() || fuelStatus.isCriticalLevelLow()) {
			System.out.println(RED + "Critical level is LOW. Evacuate staff and alert fire unit." + RESET);
		} else {
			System.out.println(
					RED + "Critical level is HIGH. Lockdown fuel shells and highlight fire extinguishers." + RESET);
		}

		// Fire protocol with delays
		delay(2000);
		unlockDoors();
		delay(2000);
		informFireUnit();
		delay(2000);
		autoFireExtinguisher();
		delay(2000);
		displayAlert(alert);

		// Check fire status
		while (true) {
			String status = getStringInput("Is the fire status normal? (yes/no): ");
			 if (status == null) {
		            System.out.println(YELLOW + "Returning to main menu..." + RESET);
		            return; // Exit to main menu
		        }
			 if (status.equalsIgnoreCase("yes")) {
				System.out.println(GREEN + "Exiting fire protocol. Returning to normal." + RESET);
				break;
			} else {
				System.out.println(RED + "Fire status not normal. Continuing fire protocol." + RESET);
			}
		}
	}

	private void handleFloodEmergency(EmergencyAlert alert) {
		System.out.println(BLUE + "\n=== Flood Emergency Protocol ===" + RESET);

		// Flood protocol with delays
		delay(2000);
		evacuateStaff();
		delay(2000);
		secureFuelTanks();
		delay(2000);
		activateFloodBarriers();
		delay(2000);
		notifyAuthorities();
		delay(2000);
		displayAlert(alert);

		// Check flood status
		while (true) {
			String status = getStringInput("Is the flood status normal? (yes/no): ");
			if (status.equalsIgnoreCase("yes")) {
				System.out.println(GREEN + "Exiting flood protocol. Returning to normal." + RESET);
				break;
			} else {
				System.out.println(RED + "Flood status not normal. Continuing flood protocol." + RESET);
			}
		}
		
	}

	private void handleCurruntLeak(EmergencyAlert alert) {
		System.out.println(BLUE + "\n=== Currunt Leak Emergency Protocol ===" + RESET);

		// Flood protocol with delays
		delay(2000);
		Informtechnician();
		delay(2000);
		displayAlert(alert);

		// Check flood status
		while (true) {
			String status = getStringInput("Is the Currunt leak status normal? (yes/no): ");
			if (status.equalsIgnoreCase("yes")) {
				System.out.println(GREEN + "Exiting Current leak protocol. Returning to normal." + RESET);
				break;
			} else {
				System.out.println(RED + "Currun leak status not normal. Continuing currun leak protocol." + RESET);
			}
		}
	}

	// fire protocole
	private void unlockDoors() {
		System.out.println(GREEN + "Doors unlocked." + RESET);
	}

	private void informFireUnit() {
		System.out.println(GREEN + "Fire unit informed." + RESET);
	}

	private void autoFireExtinguisher() {
		System.out.println(GREEN + "Fire extinguisher activated." + RESET);
	}

	private void displayAlert(EmergencyAlert alert) {
		System.out.println(RED + "ALERT: " + alert.getEmergencyType() + " emergency at " + alert.getLocation() + RESET);
	}

	// Floods
	private void evacuateStaff() {
		System.out.println(BLUE + "Evacuating all staff members..." + RESET);
	}

	private void secureFuelTanks() {
		System.out.println(BLUE + "Securing fuel tanks to prevent contamination..." + RESET);
	}

	private void activateFloodBarriers() {
		System.out.println(BLUE + "Activating flood barriers and water pumps..." + RESET);
	}

	private void notifyAuthorities() {
		System.out.println(BLUE + "Notifying local authorities about the flood..." + RESET);
	}

	// Current_leak

	private void Informtechnician() {
		System.out.println(BLUE + "Notifying technician about the Leak..." + RESET);
	}

	void delay(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void displayWelcomeMessage() {
		System.out.println(CYAN + "==========================================" + RESET);
		System.out.println(CYAN + "   FUEL STATION EMERGENCY MANAGEMENT SYSTEM   " + RESET);
		System.out.println(CYAN + "==========================================" + RESET);
		System.out.println(YELLOW + "Welcome! Please follow the instructions below." + RESET);
	}

	private void displayEmergencyMenu() {
		System.out.println(CYAN + "\n=== Emergency Menu ===" + RESET);
		System.out.println("1. Fire🔥");
		System.out.println("2. Flood🌊");
		System.out.println("3. Electric Leak⚡");
		System.out.println("4. Other");
		System.out.println("5. Back To Main Menu");
		

	}

	private Integer getIntInput(String prompt, int min, int max) {
	    while (true) {
	        System.out.print(YELLOW + prompt + RESET);
	        String input = sc.nextLine().trim();
	        if (input.equalsIgnoreCase("e")) {
	            return null; // Indicates the user wants to exit
	        }
	        try {
	            int value = Integer.parseInt(input);
	            if (value >= min && value <= max) {
	                return value;
	            }
	        } catch (NumberFormatException e) {
	            // Ignore and continue
	        }
	        System.out.println(RED + "Invalid input. Please enter a number between " + min + " and " + max + " or 'e' to exit." + RESET);
	    }
	}

	private String getStringInput(String prompt) {
	    System.out.print(YELLOW + prompt + RESET);
	    String input = sc.nextLine().trim();
	    if (input.equalsIgnoreCase("e")) {
	        return null; // Indicates the user wants to exit
	    }
	    return input;
	}
	
	private Boolean getBooleanInput(String prompt) {
	    while (true) {
	        System.out.print(YELLOW + prompt + RESET);
	        String input = sc.nextLine().trim().toLowerCase();
	        if (input.equalsIgnoreCase("e")) {
	            return null; // Indicates the user wants to exit
	        }
	        if (input.equals("true") || input.equals("false")) {
	            return Boolean.parseBoolean(input);
	        }
	        System.out.println(RED + "Invalid input. Please enter 'true', 'false', or 'e' to exit." + RESET);
	    }
	}

	public static void main(String[] args) {
		EmergencyPublisher cli = new EmergencyPublisher();
		cli.start();
	}

}
