package com.fuel.dispatchedtransactions;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class TransactionActivator implements BundleActivator {

	 private ServiceRegistration<?> registration;

	    @Override
	    public void start(BundleContext context) throws Exception {
	        TransactionsService transactionService = new TransactionServiceImpl();
	        registration = context.registerService(TransactionsService.class.getName(), transactionService, null);
	        System.out.println("🚀 Transactions Service registered successfully.");
	    }

	    @Override
	    public void stop(BundleContext context) throws Exception {
	        registration.unregister();
	        System.out.println("🛑 Transactions service unregistered successfully.");
	    }
}
