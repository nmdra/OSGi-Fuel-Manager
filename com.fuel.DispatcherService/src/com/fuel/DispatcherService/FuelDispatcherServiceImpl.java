package com.fuel.DispatcherService;

import java.util.Scanner;

import com.fuel.dispatcherfuelService.FuelStorage;
import com.fuel.dispatcherfuelService.FuelType;
import com.fuel.dispatcherfuelService.Transaction;

public class FuelDispatcherServiceImpl implements FuelDispatcherService {
	private int pumpNumber;
	private String selectedFuelType;
	private double calculatedLiters = 0;
	private double calculatedCost = 0;

	Scanner scanner = new Scanner(System.in);

	@Override
	public void selectPump(int pumpNumber) {
		this.pumpNumber = pumpNumber;
		System.out.println("Pump " + pumpNumber + " selected.");
	}

	@Override
	public void selectFuelType(String fuelType) {
		FuelType fuel = FuelStorage.getFuelType(fuelType);
		if (fuel == null) {
			System.out.println("Invalid fuel type. Please select a valid fuel.");
		} else {
			this.selectedFuelType = fuelType;
			System.out.println("Fuel type selected: " + fuelType);
		}
	}

	@Override
	public double calculateByRupees(double rupees) {
		FuelType fuel = FuelStorage.getFuelType(selectedFuelType);
		if (fuel == null) {
			System.out.println("Please select a valid fuel type first.");
			return 0;
		}
		calculatedLiters = rupees / fuel.getPricePerLiter();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out
				.println("For Rs. " + rupees + ", you will get " + calculatedLiters + " liters of " + selectedFuelType);
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		calculatedCost = rupees;
		return calculatedLiters;
	}

	@Override
	public double calculateByLiters(double liters) {
		FuelType fuel = FuelStorage.getFuelType(selectedFuelType);
		if (fuel == null) {
			System.out.println("Please select a valid fuel type first.");
			return 0;
		}
		calculatedCost = liters * fuel.getPricePerLiter();
		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.out.println("For " + liters + " liters, the total cost will be Rs. " + calculatedCost);
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");

		calculatedLiters = liters;
		return calculatedCost;
	}

	@Override
	public void confirmTransaction() {
		System.out.println("Debug: Selected Fuel Type = " + selectedFuelType);

		if (selectedFuelType == null || calculatedLiters <= 0) {
			System.out.println("Transaction not valid. Please select fuel and enter amount.");
			return;
		}

		FuelType fuel = FuelStorage.getFuelType(selectedFuelType);
//        System.out.println("Before Update - Fuel Stock: " + fuel.getAvailableLiters() + " liters");

		if (fuel.getAvailableLiters() < calculatedLiters) {
			System.out.println("Not enough fuel in storage! Transaction canceled.");
			return;
		}

		FuelStorage.updateFuelStock(selectedFuelType, calculatedLiters);
		System.out.println("After Update - Fuel Stock: "
				+ FuelStorage.getFuelType(selectedFuelType).getAvailableLiters() + " liters");

		Transaction transaction = new Transaction(pumpNumber, selectedFuelType, calculatedCost, calculatedLiters,
				calculatedCost);
		Transaction.recordTransaction(transaction);

		System.out.println("Transaction Successful!");
		System.out.println("Fuel Dispensed: " + calculatedLiters + " liters");

		System.out.println("Total Cost: Rs. " + calculatedCost);
	}

	@Override
	public void cancelOperation() {
		System.out.println("Operation canceled. Returning to main menu.");
	}
}