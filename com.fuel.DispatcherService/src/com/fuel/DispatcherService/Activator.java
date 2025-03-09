package com.fuel.DispatcherService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import com.fuel.dispatchedtransactions.TransactionsService;
import com.fuel.fuelservice.IFuelService;
import com.fuel.notificationsservice.INotificationService; // Added clearly

public class Activator implements BundleActivator {
	private ServiceRegistration<?> registration;
	private ServiceReference<TransactionsService> transactionsReference;
	private ServiceReference<INotificationService> notificationReference; // clearly added
	private ServiceReference<IFuelService> fuelserviceReference; // fuel service

	@Override
	public void start(BundleContext context) {
		transactionsReference = context.getServiceReference(TransactionsService.class);
		notificationReference = context.getServiceReference(INotificationService.class); // clearly added
		fuelserviceReference = context.getServiceReference(IFuelService.class); //fuel service

		if (transactionsReference != null) {
			TransactionsService transactionsService = context.getService(transactionsReference);
			INotificationService notificationService = context.getService(notificationReference); // clearly added
			IFuelService fuelService = context.getService(fuelserviceReference);

			FuelDispatcherService dispatcherService = new FuelDispatcherServiceImpl(transactionsService, notificationService, fuelService);
			registration = context.registerService(FuelDispatcherService.class.getName(), dispatcherService, null);
			
			System.out.println("✅ Dispatcher Service with Transaction Integration Started.");
			System.out.println("✅ Notification Integration Started.");

		} else {
			System.out.println("❌ Transaction Service unavailable.");
		}
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		System.out.println("Fuel Dispatcher Service Stopped.");
	}
}