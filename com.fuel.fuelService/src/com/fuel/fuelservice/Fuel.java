package com.fuel.fuelservice;

public class Fuel {
    private String type;
    private double price;
    private double quantity;

    public Fuel(String type, double price, double quantity) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public double getQuantity() { return quantity; }

    public void setPrice(double price) { this.price = price; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}