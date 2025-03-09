package com.fuel.dispatchedtransactions;

import java.util.List;

public interface TransactionsService {
    void addTransaction(TransactionsModel transaction);
    List<TransactionsModel> getTransactions();
}
