package com.fuelstation.emergencypublisher;

import org.osgi.framework.BundleActivator; 
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.fuelstation.emergencymodel.EmergencyService;

public class EmergencyPublisherActivator implements BundleActivator {

	 private ServiceRegistration<EmergencyService> registration;
	 private EmergencyPublisher emergencyPublisher;

	    @Override
	    public void start(BundleContext context) throws Exception {
	        try {
	            System.out.println("Emergency Publisher Bundle Started.");

	            // Create an instance of EmergencyPublisher
	            emergencyPublisher = new EmergencyPublisher();

	            // Create an instance of EmergencyServiceImpl with EmergencyPublisher
	            EmergencyService emergencyService = new EmergencyServiceImpl(emergencyPublisher);

	            // Register the EmergencyService
	            registration = context.registerService(EmergencyService.class, emergencyService, null);

	            // Start the CLI interface
	            emergencyPublisher.start();
	        } catch (Exception e) {
	            System.err.println("Error starting Emergency Publisher Bundle: " + e.getMessage());
	            e.printStackTrace();
	            throw e; // Re-throw the exception to mark the bundle as failed
	        }
	    }

	    @Override
	    public void stop(BundleContext context) throws Exception {
	        System.out.println("Emergency Publisher Bundle Stopped.");

	        // Unregister the service
	        if (registration != null) {
	            registration.unregister();
	        }

	        // Clean up
	        emergencyPublisher = null;
	    }
}
