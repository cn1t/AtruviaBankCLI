package com.nite;

import java.text.SimpleDateFormat;
import java.util.*;

class Transaction {
    private String direction;
    private String accountOwner;
    private String secondUser;
    private int amount;
    private Date timestamp;

    public Transaction(String direction, String accountOwner, String secondUser, int amount) {
        this.direction = direction;
        this.accountOwner = accountOwner;
        this.secondUser = secondUser;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s | %s | %s | %s | %d€", sdf.format(timestamp), accountOwner, direction, secondUser, amount);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void sendMoney(String direction, String accountOwner, String secondUser, int amount) {
        if (amount > 0) {
            Transaction transaction = new Transaction(direction, accountOwner, secondUser, amount);
            transactions.add(0, transaction); // Add the new transaction to the top
            Collections.sort(transactions, Comparator.comparing(Transaction::getTimestamp)); // Sort transactions by timestamp
        }
    }

    public void displayTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
