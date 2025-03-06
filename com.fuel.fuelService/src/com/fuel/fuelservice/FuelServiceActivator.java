package com.fuel.fuelservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class FuelServiceActivator implements BundleActivator {
    private ServiceRegistration<?> fuelServiceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("⛽ Fuel Service Started...");
        FuelServiceImpl fuelService = new FuelServiceImpl(context);
        fuelServiceRegistration = context.registerService(IFuelService.class.getName(), fuelService, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("🛑 Fuel Service Stopped.");
        fuelServiceRegistration.unregister();
    }
}