package com.fuel.dispatchedtransactions;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionsService {
    private final List<TransactionsModel> transactions = new ArrayList<>();

    @Override
    public void addTransaction(TransactionsModel transaction) {
        transactions.add(transaction);
        System.out.println("✅ Transaction added successfully.");
    }

    @Override
    public List<TransactionsModel> getTransactions() {
        return transactions;
    }

	

	
}
