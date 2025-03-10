package com.fuel.authpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class AuthServiceActivator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        IAuthService authService = new AuthServiceImpl();
        context.registerService(IAuthService.class, authService, null);
        System.out.println("🔐 Authentication Service started!");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("🔒 Authentication Service stopped.");
    }
}
