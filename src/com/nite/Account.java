package com.nite;
import static com.nite.Hash.checkPasswordHash;

public class Account {
    private double balance;
    private String ownerName;
    private String passwordHash;
    private String  hashSalt;
    private TransactionManager transactionManager;

    public Account(double balance, String ownerName, String passwordHash, String hashSalt, TransactionManager transactionManager) {
        this.balance = balance;
        this.ownerName = ownerName;
        this.passwordHash = passwordHash;
        this.hashSalt = hashSalt;
        this.transactionManager = new TransactionManager();
    }

    public double getBalance() {
        return balance;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public String getName() {
        return ownerName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getHashSalt() {
        return hashSalt;
    }

    public boolean checkPassword(String password) throws Exception {
        return checkPasswordHash(password, hashSalt, passwordHash);
    }

    public void setNewPassword(String newPassword, String newSalt) {
        this.passwordHash = newPassword;
        this.hashSalt = newSalt;
    }
}
