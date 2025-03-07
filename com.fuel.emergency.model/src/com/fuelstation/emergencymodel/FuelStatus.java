package com.fuelstation.emergencymodel;

public class FuelStatus {
	private boolean isTankEmpty;
	private boolean isCriticalLevelLow;
	
	public FuelStatus(boolean isTankEmpty, boolean isCriticalLevelLow) {
		this.isTankEmpty=isTankEmpty;
		this.isCriticalLevelLow = isCriticalLevelLow;
		
	}
	
    public boolean isTankEmpty() {
        return isTankEmpty;
    }

    public boolean isCriticalLevelLow() {
        return isCriticalLevelLow;
    }
    


}
