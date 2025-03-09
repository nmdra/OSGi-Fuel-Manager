package staff_producer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class StaffActivator implements BundleActivator {

	@SuppressWarnings("rawtypes")
	ServiceRegistration publishServiceRegistration;
	
    public void start(BundleContext context) throws Exception {
		
		System.out.println("Staff Producer Start");
	
		StaffService StaffService = new StaffServiceImpl();
		
		publishServiceRegistration = context.registerService(StaffService.class.getName(), StaffService, null);
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Staff Publisher Stop");
		publishServiceRegistration.unregister();
	}
}