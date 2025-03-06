package com.fuel.notificationsservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class NotificationActivator implements BundleActivator {
    private ServiceRegistration<?> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        INotificationService notificationService = new NotificationServiceImpl();
        serviceRegistration = context.registerService(INotificationService.class.getName(), notificationService, null);
        System.out.println("📩 Notification Service Started.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("❌ Notification Service Stopped.");
        serviceRegistration.unregister();
    }
}

