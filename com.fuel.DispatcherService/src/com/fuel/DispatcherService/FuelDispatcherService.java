package com.fuel.DispatcherService;

public interface FuelDispatcherService {
    void selectPump(int pumpNumber);
    void selectFuelType(String fuelType);
    double calculateByRupees(double rupees);
    double calculateByLiters(double liters);
    void confirmTransaction();
    void cancelOperation();
}
