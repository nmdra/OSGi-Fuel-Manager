package com.fuel.dispatchedtransactions;

import java.time.LocalDateTime;

public class TransactionsModel {
    private int pumpNumber;
    private String fuelType;
    private double amount;
    private double litersDispensed;
    private double totalCost;
    private LocalDateTime transactionTime;

    public TransactionsModel(int pumpNumber, String fuelType, double amount, double litersDispensed, double totalCost) {
        this.pumpNumber = pumpNumber;
        this.fuelType = fuelType;
        this.amount = amount;
        this.litersDispensed = litersDispensed;
        this.totalCost = totalCost;
        this.transactionTime = LocalDateTime.now();
    }

    // Getters
    public int getPumpNumber() { return pumpNumber; }
    public String getFuelType() { return fuelType; }
    public double getAmount() { return amount; }
    public double getLitersDispensed() { return litersDispensed; }
    public double getTotalCost() { return totalCost; }
    public LocalDateTime getTransactionTime() { return transactionTime; }

    @Override
    public String toString() {
        return String.format("Pump: %d | Fuel: %s | Amount: %.2f | Liters: %.2f | Cost: %.2f | Time: %s",
            pumpNumber, fuelType, amount, litersDispensed, totalCost, transactionTime);
    }
}
