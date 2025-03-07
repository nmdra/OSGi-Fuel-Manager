package com.fuelstation.emergency.consumer;

import org.osgi.framework.BundleActivator; 
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.fuelstation.emergencymodel.EmergencyService;
import com.fuelstation.emergencymodel.EmergencyAlert;
import com.fuelstation.emergencymodel.EmergencyType;
import com.fuelstation.emergencymodel.FuelStatus;

public class EmengercyConsumerActivator implements BundleActivator {

    private ServiceReference<EmergencyService> serviceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Emergency Consumer Bundle Started.");
        serviceReference = context.getServiceReference(EmergencyService.class);
        if (serviceReference != null) {
            EmergencyService emergencyService = context.getService(serviceReference);

            // Simulate an emergency
            FuelStatus fuelStatus = new FuelStatus(false, false); // Example: Tank is not empty, critical level is not low
            EmergencyAlert alert = new EmergencyAlert("Pump 1", EmergencyType.Fire, fuelStatus);
            emergencyService.handleEmergency(alert);
        } else {
            System.out.println("EmergencyService not found!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Emergency Consumer Bundle Stopped.");
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }

}
