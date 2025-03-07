package com.fuel.dispatcherfuelService;

import java.time.LocalDateTime;

import java.util.ArrayList;

public class Transaction {
    private int pumpNumber;
    private String fuelType;
    private double amount;
    private double litersDispensed;
    private double totalCost;

    private static ArrayList<Transaction> transactionLog = new ArrayList<>();

    public Transaction(int pumpNumber, String fuelType, double amount, double litersDispensed, double totalCost) {
        this.pumpNumber = pumpNumber;
        this.fuelType = fuelType;
        this.amount = amount;
        this.litersDispensed = litersDispensed;
        this.totalCost = totalCost;
    }

    public static void recordTransaction(Transaction transaction) {
        transactionLog.add(transaction);
    }

    public static ArrayList<Transaction> getTransactionLog() {
        return transactionLog;
    }
}