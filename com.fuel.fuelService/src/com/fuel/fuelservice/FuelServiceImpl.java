package com.fuel.fuelservice;

import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.fuel.notificationsservice.*;

public class FuelServiceImpl implements IFuelService {
    private final Map<String, Double> fuelInventory = new HashMap<>();
    private final Map<String, Double> fuelPrices = new HashMap<>();
    private INotificationService notificationService;

    public FuelServiceImpl(BundleContext context) {
        ServiceReference<?> ref = context.getServiceReference(INotificationService.class.getName());
        if (ref != null) {
            notificationService = (INotificationService) context.getService(ref);
        }
    }

    @Override
    public void addFuelType(String type, double price, double quantity) {
        fuelInventory.put(type, quantity);
        fuelPrices.put(type, price);
        System.out.println("Added fuel type: " + type);
        if (notificationService != null) {
            notificationService.addNotification("Added fuel: " + type, "INFO");
        }
    }

    @Override
    public void updateFuelLevel(String type, double quantity) {
        fuelInventory.put(type, fuelInventory.getOrDefault(type, 0.0) + quantity);
        System.out.println("Updated fuel level for: " + type);
        if (notificationService != null) {
            notificationService.addNotification("Updated fuel level: " + type, "INFO");
        }
    }

    @Override
    public double checkFuelLevel(String type) {
        return fuelInventory.getOrDefault(type, 0.0);
    }

    @Override
    public void orderFuelTruck(String type, double quantity) {
        System.out.println("Ordering " + quantity + "L of " + type);
        if (notificationService != null) {
            notificationService.addNotification("Ordered fuel truck: " + type + " - " + quantity + "L", "HIGH");
        }
    }
    
    public void reduceFuelQuantity(String type, double quantity) {
        if (fuelInventory.containsKey(type)) {
            double currentQuantity = fuelInventory.get(type);
            if (currentQuantity >= quantity) {
                fuelInventory.put(type, currentQuantity - quantity);
                System.out.println("Reduced " + quantity + " of " + type + ". New quantity: " + (currentQuantity - quantity));
            } else {
                System.out.println("Not enough " + type + " to reduce.");
            }
        } else {
            System.out.println("Fuel type " + type + " not found.");
        }
    }
}
