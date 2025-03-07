package com.fuelstation.emergencymodel;

public class EmergencyAlert {

	private String location;
	private EmergencyType emergencyType;
	private FuelStatus fuelStatus;
	
	public EmergencyAlert(String location,EmergencyType emergencyType,FuelStatus fuelStatus) {
        this.location = location;
        this.emergencyType = emergencyType;
        this.fuelStatus = fuelStatus;
        
	}
	
	public String getLocation() {
		return location;
	}
	
    public EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public FuelStatus getFuelStatus() {
        return fuelStatus;
    }
	
}
