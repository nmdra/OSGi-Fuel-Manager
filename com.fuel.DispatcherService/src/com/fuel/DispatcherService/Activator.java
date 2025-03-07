package com.fuel.DispatcherService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    @SuppressWarnings("rawtypes")
	private ServiceRegistration registration;

    @Override
    public void start(BundleContext context) throws Exception {
        FuelDispatcherService service = new FuelDispatcherServiceImpl();
        registration = context.registerService(FuelDispatcherService.class.getName(), service, null);
        System.out.println("Fuel Dispatcher Service Registered.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("Fuel Dispatcher Service Stopped.");
    }
}