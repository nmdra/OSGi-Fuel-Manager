package com.fuel.fuelservice;

import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.fuel.notificationsservice.INotificationService;

public class FuelServiceImpl implements IFuelService {
    
    private final Map<FuelType, Double> fuelInventory = new HashMap<>();
    private final Map<FuelType, Double> fuelPrices = new HashMap<>();
    private INotificationService notificationService;

    public FuelServiceImpl(BundleContext context) {
        ServiceReference<?> ref = context.getServiceReference(INotificationService.class.getName());
        if (ref != null) {
            notificationService = (INotificationService) context.getService(ref);
        }
    }

    @Override
    public void addFuelType(String type, double price, double quantity) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase()); // Validate Fuel Type
            fuelInventory.put(fuelType, quantity);
            fuelPrices.put(fuelType, price);
            System.out.println("✅ Added fuel type: " + fuelType + " (Price: " + price + ", Quantity: " + quantity + ")");

            if (notificationService != null) {
                notificationService.addNotification("Added fuel: " + fuelType, "INFO");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
        }
    }

    @Override
    public void updateFuelLevel(String type, double quantity) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());
            fuelInventory.put(fuelType, fuelInventory.getOrDefault(fuelType, 0.0) + quantity);
            System.out.println("✅ Updated fuel level for: " + fuelType + " (New quantity: " + fuelInventory.get(fuelType) + ")");

            if (notificationService != null) {
                notificationService.addNotification("Updated fuel level: " + fuelType, "INFO");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
        }
    }

    @Override
    public double checkFuelLevel(String type) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());
            return fuelInventory.getOrDefault(fuelType, 0.0);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
            return -1; // Return -1 to indicate an error
        }
    }

    @Override
    public void orderFuelTruck(String type, double quantity) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());
            System.out.println("🚛 Ordering " + quantity + "L of " + fuelType);

            if (notificationService != null) {
                notificationService.addNotification("Ordered fuel truck: " + fuelType + " - " + quantity + "L", "HIGH");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
        }
    }

    public void reduceFuelQuantity(String type, double quantity) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());

            if (fuelInventory.containsKey(fuelType)) {
                double currentQuantity = fuelInventory.get(fuelType);
                if (currentQuantity >= quantity) {
                    fuelInventory.put(fuelType, currentQuantity - quantity);
                    System.out.println("✅ Reduced " + quantity + "L of " + fuelType + ". New quantity: " + (currentQuantity - quantity));
                } else {
                    System.out.println("⚠️ Not enough " + fuelType + " available to reduce.");
                }
            } else {
                System.out.println("❌ Fuel type " + fuelType + " not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
        }
    }
}

