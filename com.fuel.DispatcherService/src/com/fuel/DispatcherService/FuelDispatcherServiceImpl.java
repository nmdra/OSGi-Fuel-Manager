package com.fuel.DispatcherService;

import java.util.Scanner;

import com.fuel.dispatcherfuelService.FuelStorage;
import com.fuel.dispatcherfuelService.FuelType;
import com.fuel.dispatchedtransactions.TransactionsService;
import com.fuel.dispatchedtransactions.TransactionsModel;
import com.fuel.notificationsservice.INotificationService;
import com.fuel.fuelservice.*;


public class FuelDispatcherServiceImpl implements FuelDispatcherService {
	private int pumpNumber;
	private String selectedFuelType;
	private double calculatedLiters = 0;
	private double calculatedCost = 0;
	private TransactionsService transactionsService; // Reference for transactions storage
	private INotificationService notificationService;
	private IFuelService fuelService;

	final double LOW_FUEL_THRESHOLD = 100.0; // Example threshold for low fuel notifications

	
	
	final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";
    final String BLUE = "\u001B[34m";
    final String CYAN = "\u001B[36m";
    final String BOLD = "\u001B[1m";


	Scanner scanner = new Scanner(System.in);

	public FuelDispatcherServiceImpl(TransactionsService transactionsService, INotificationService notificationService, IFuelService fuelService) {
		this.transactionsService = transactionsService;
		this.notificationService = notificationService;
		this.fuelService = fuelService;
	}
	
	@Override
	public void selectPump(int pumpNumber) {
		this.pumpNumber = pumpNumber;
		System.out.println(GREEN+BOLD+"Pump " + pumpNumber + " selected."+RESET+BOLD);
	}

	@Override
	public void selectFuelType(String fuelType) {
		FuelType fuel = FuelStorage.getFuelType(fuelType);
		if (fuel == null) {
			System.out.println(BOLD+RED+"Invalid fuel type. Please select a valid fuel."+RESET+BOLD);
		} else {
			this.selectedFuelType = fuelType;
			System.out.println("Fuel type selected: " + fuelType);
		}
	}

	@Override
	public double calculateByRupees(double rupees) {
		FuelType fuel = FuelStorage.getFuelType(selectedFuelType);
		if (fuel == null) {
			System.out.println(BOLD+RED+"Please select a valid fuel type first."+RESET+BOLD);
			return 0;
		}
		calculatedLiters = rupees / fuel.getPricePerLiter();
		
		System.out.println("");
		System.out
				.println(BOLD+RED+"For Rs. " + rupees + ", you will get " + calculatedLiters + " liters of " + selectedFuelType+RESET+BOLD);
		System.out.println("");
		System.out.println("");
	
		calculatedCost = rupees;
		return calculatedLiters;
	}

	@Override
	public double calculateByLiters(double liters) {
		FuelType fuel = FuelStorage.getFuelType(selectedFuelType);
		if (fuel == null) {
			System.out.println(BOLD+RED+"Please select a valid fuel type first."+RESET+BOLD);
			return 0;
		}
		calculatedCost = liters * fuel.getPricePerLiter();
		
		System.out.println("");

		System.out.println(BOLD+RED+"For " + liters + " liters, the total cost will be Rs. " + calculatedCost+RESET+BOLD);
		System.out.println("");
		System.out.println("");
		

		calculatedLiters = liters;
		return calculatedCost;
	}

	@Override
	public void confirmTransaction() {
		System.out.println(BOLD+"Selected Fuel Type = " +GREEN+BOLD+ selectedFuelType+RESET+BOLD);

		if (selectedFuelType == null || calculatedLiters <= 0) {
			System.out.println(BOLD+RED+"Transaction not valid. Please select fuel and enter amount."+RESET+BOLD);
			return;
		}

		FuelType fuel = FuelStorage.getFuelType(selectedFuelType);
//        System.out.println("Before Update - Fuel Stock: " + fuel.getAvailableLiters() + " litres");

		if (fuel.getAvailableLiters() < calculatedLiters) {
			System.out.println(BOLD+RED+"Not enough fuel in storage! Transaction canceled."+RESET+BOLD);
			
			String message = "⚠️ Fuel type " + selectedFuelType + " low on storage! please order more." ;
			String priority = "HIGH";

			notificationService.addNotification(message, priority);
			return;
		}

		FuelStorage.updateFuelStock(selectedFuelType, calculatedLiters);
		System.out.println(BOLD+"After Update - Fuel Stock: "+GREEN+BOLD
				+ FuelStorage.getFuelType(selectedFuelType).getAvailableLiters() + RESET+BOLD+" liters");

		TransactionsModel transaction = new TransactionsModel(
				pumpNumber, selectedFuelType, calculatedCost, calculatedLiters, calculatedCost
			);
			transactionsService.addTransaction(transaction);
			System.out.println(BOLD + CYAN + "✅ Transaction confirmed and recorded." + RESET);

		// fuel service
		fuelService.reduceFuelQuantity(selectedFuelType,calculatedLiters);
		notificationService.addNotification("Fuel Dispenced: " + calculatedLiters + "litres", "INFO");
		
		System.out.println(BOLD+CYAN+"Transaction Successful!"+RESET+BOLD);
		System.out.println(BOLD+"Fuel Dispensed: " +GREEN+ calculatedLiters +RESET+ BOLD+" liters"+RESET+BOLD);

		System.out.println(BOLD+"Total Cost: Rs. " +GREEN+ calculatedCost+RESET+BOLD);

	}
	
	@Override
	public void cancelOperation() {
		System.out.println(BOLD+RED+"Operation canceled. Returning to main menu."+RESET+BOLD);
	}
}