package com.fuel.dispatcherfuelService;

public class FuelType {
    private String name;
    private double pricePerLiter;
    private double availableLiters;

    public FuelType(String name, double pricePerLiter, double availableLiters) {
        this.name = name;
        this.pricePerLiter = pricePerLiter;
        this.availableLiters = availableLiters;
    }

    public String getName() {
        return name;
    }

    public double getPricePerLiter() {
        return pricePerLiter;
    }

    public double getAvailableLiters() {
        return availableLiters;
    }

    public void setAvailableLiters(double availableLiters) {
        this.availableLiters = availableLiters;
    }
}
