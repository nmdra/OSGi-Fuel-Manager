package com.fuel.fuelservice;

public interface IFuelService {
    void addFuelType(String type, double price, double quantity);
    void updateFuelLevel(String type, double quantity);
    double checkFuelLevel(String type);
    void orderFuelTruck(String type, double quantity);
    void reduceFuelQuantity(String type, double quantity);
}