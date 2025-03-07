package com.fuel.dispatcherfuelService;

import java.util.HashMap;


public class FuelStorage {
    private static HashMap<String, FuelType> fuelStock = new HashMap<>();

    static {
        fuelStock.put("95 Octane", new FuelType("95 Octane", 100.0, 5000));
        fuelStock.put("92 Octane", new FuelType("92 Octane", 80.0, 6000));
        fuelStock.put("Diesel", new FuelType("Diesel", 60.0, 7000));
        fuelStock.put("Petrol", new FuelType("Petrol", 90.0, 5500));
    }

    public static FuelType getFuelType(String name) {
        return fuelStock.get(name);
    }

    public static void updateFuelStock(String name, double litersUsed) {
        FuelType fuel = fuelStock.get(name);
        if (fuel != null) {
            double newAmount = fuel.getAvailableLiters() - litersUsed;
            if (newAmount < 0) {
                newAmount = 0; // Prevents negative values
            }
            fuel.setAvailableLiters(newAmount);
            fuelStock.put(name, fuel);  // Ensure HashMap updates the reference
        }
    }
    public static HashMap<String, FuelType> getFuelStock() {
        return fuelStock;
    }
}