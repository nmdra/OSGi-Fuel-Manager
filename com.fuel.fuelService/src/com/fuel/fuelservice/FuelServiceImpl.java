package com.fuel.fuelservice;

import java.util.HashMap;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.fuel.notificationsservice.INotificationService;

public class FuelServiceImpl implements IFuelService {
    
    private final Map<FuelType, Fuel> fuelInventory = new HashMap<>();
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
            
            // Check if fuel already exists, update it instead of creating a new one
            Fuel fuel = fuelInventory.get(fuelType);
            if (fuel == null) {
                fuel = new Fuel(fuelType.name(), price, quantity);
                fuelInventory.put(fuelType, fuel);
                System.out.println("✅ Added new fuel type: " + fuelType + " (Price: " + price + ", Quantity: " + quantity + ")");
            } else {
                fuel.setPrice(price);
                fuel.setQuantity(quantity);
                System.out.println("🔄 Updated existing fuel type: " + fuelType + " (New Price: " + price + ", New Quantity: " + quantity + ")");
            }

            // Send notification if available
            if (notificationService != null) {
                notificationService.addNotification("Fuel updated: " + fuelType + " (Price: " + price + ", Quantity: " + quantity + ")", "INFO");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
        }
    }

    @Override
    public void updateFuelLevel(String type, double quantity) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());
            Fuel fuel = fuelInventory.get(fuelType);
            
            if (fuel != null) {
                double currentQuantity = fuel.getQuantity();
                double newQuantity = currentQuantity + quantity;
                
                // Ensure the fuel level doesn't go below zero
                if (newQuantity < 0) {
                    System.out.println("❌ Cannot reduce fuel level below zero for: " + fuelType);
                    return;
                }
                
                // Update the fuel quantity
                fuel.setQuantity(newQuantity);
                System.out.println("✅ Updated fuel level for: " + fuelType + " (New quantity: " + fuel.getQuantity() + ")");
                
                if (notificationService != null) {
                    notificationService.addNotification("Updated fuel level: " + fuelType, "INFO");
                }
            } else {
                System.out.println("❌ Fuel type " + fuelType + " not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
        }
    }


    @Override
    public double checkFuelLevel(String type) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());
            Fuel fuel = fuelInventory.get(fuelType);
            if (fuel != null) {
                return fuel.getQuantity();
            } else {
                System.out.println("❌ Fuel type " + fuelType + " not found.");
                return -1; // Return -1 to indicate an error
            }
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

    // TODO
    public void reduceFuelQuantity(String type, double quantity) {
        try {
            FuelType fuelType = FuelType.valueOf(type.toUpperCase());
            Fuel fuel = fuelInventory.get(fuelType);

            if (fuel != null) {
                double currentQuantity = fuel.getQuantity();
                if (currentQuantity >= quantity) {
                    fuel.setQuantity(currentQuantity - quantity); // Reduce fuel quantity
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

	@Override
	public void updateFuelPrice(String name, double newPrice) {
	    try {
	        FuelType fuelType = FuelType.valueOf(name.toUpperCase());
	        Fuel fuel = fuelInventory.get(fuelType);

	        if (fuel != null) {
	            fuel.setPrice(newPrice);
	            System.out.println("✅ Updated price for " + fuelType + " to $" + newPrice);

	            if (notificationService != null) {
	                notificationService.addNotification("Updated fuel price: " + fuelType + " - $" + newPrice, "INFO");
	            }
	        } else {
	            System.out.println("⚠️ Fuel type " + fuelType + " not found.");
	        }
	    } catch (IllegalArgumentException e) {
	        System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
	    }
	}

	@Override
	public double getFuelPrice(String name) {
	    try {
	        FuelType fuelType = FuelType.valueOf(name.toUpperCase());
	        Fuel fuel = fuelInventory.get(fuelType);

	        if (fuel != null) {
	            return fuel.getPrice();
	        } else {
	            System.out.println("⚠️ Fuel type " + fuelType + " not found.");
	            return -1; // Indicate that the fuel type was not found
	        }
	    } catch (IllegalArgumentException e) {
	        System.out.println("❌ Invalid fuel type! Allowed types: OCTANE_92, OCTANE_95, DIESEL, KEROSENE.");
	        return -1; // Indicate an error
	    }
	}

	
}

