package com.fuelstation.emergencypublisher;

import com.fuelstation.emergencymodel.EmergencyAlert;
import com.fuelstation.emergencymodel.EmergencyService;

public class EmergencyServiceImpl implements EmergencyService {

	private EmergencyPublisher emergencyPublisher;

	public EmergencyServiceImpl(EmergencyPublisher emergencyPublisher) {
		this.emergencyPublisher = emergencyPublisher;
	}

	@Override
	public void handleEmergency(EmergencyAlert alert) {

		System.out.println("Handling emergency at: " + alert.getLocation());
		// Delegate to the EmergencyPublisher logic

		emergencyPublisher.handleEmergency(alert);

	}

}
