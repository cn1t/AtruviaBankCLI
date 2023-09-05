package com.nite;

import java.util.concurrent.TimeUnit;

import static com.nite.Hash.*;
import static com.nite.Util.*;
import static java.lang.Integer.parseInt;

public class BankingApp {
    private static Account sampleAccount; // Declare sampleAccount as a class field
    private static TransactionManager transactionManager;

    public static void main(String[] args) throws Exception {
        // Set default values into the account
        String defaultName = "Max Mustermann";
        String defaultPassword = "passwort123";

        int min = 5; // Minimum random balance
        int max = 1000; // Maximum random balance

        int defaultBalance = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String[] passwordArray = createHashedPassword(defaultPassword);

        String passwordHash = passwordArray[0];
        String passwordSalt = passwordArray[1];

        sampleAccount = new Account(defaultBalance, defaultName, passwordHash, passwordSalt, transactionManager);

        transactionManager = new TransactionManager();
        transactionManager.sendMoney("<--", defaultName, "David Braun", (int) Math.floor(Math.random() * (max - min + 1) + min));
        transactionManager.sendMoney("-->", defaultName, "Max Schmidt", (int) Math.floor(Math.random() * (max - min + 1) + min));
        transactionManager.sendMoney("-->", defaultName, "Lisa Müller", (int) Math.floor(Math.random() * (max - min + 1) + min));
        transactionManager.sendMoney("<--", defaultName, "Tom Fischer", (int) Math.floor(Math.random() * (max - min + 1) + min));
        transactionManager.sendMoney("<--", defaultName, "Julia Weber", (int) Math.floor(Math.random() * (max - min + 1) + min));

        // Start

        System.out.println(asciiBanner);
        System.out.println("Willkommen zum Atruvia Bank System!");
        manageOptions(new String[]{"Login"});
    }

    public static void login() throws Exception {
        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Bitte geben Sie Ihren Namen ein.");

        String name = handleTextInput();

        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Bitte geben Sie Ihr Passwort ein.");

        String password = handleTextInput();

        boolean isAuthenticatedName = sampleAccount.getName().equals(name);
        boolean isAuthenticatedPassword = sampleAccount.checkPassword(password);

        while (!isAuthenticatedName || !isAuthenticatedPassword) {
            clearTerminal();
            System.out.println(asciiBanner);
            System.out.println("Ungültiger Name / Passwort. Bitte versuchen Sie es erneut.");
            System.out.println("Bitte geben Sie Ihren Namen ein.");

            name = handleTextInput();

            clearTerminal();
            System.out.println(asciiBanner);
            System.out.println("Bitte geben Sie Ihr Passwort ein.");

            password = handleTextInput();

            isAuthenticatedName = sampleAccount.getName().equals(name);
            isAuthenticatedPassword = sampleAccount.checkPassword(password);
        }

        System.out.println("Erfolgreich eingeloggt!");
        TimeUnit.SECONDS.sleep(1);

        menue();
    }

    public static void menue() {
        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Hallo " + sampleAccount.getName() + "! Was möchten Sie tun?");
        manageOptions(new String[]{"Guthaben sehen", "Geld versenden", "Transaktionen", "Passwort zurücksetzen"});
    }

    public static void guthaben_sehen() {
        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Du hast " + sampleAccount.getBalance() + "€ auf dem Konto.");
        manageOptions(new String[]{"Menü"});
    }

    public static void geld_versenden() throws InterruptedException {
        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Bitte geben Sie den Geldbetrag an, welchen Sie versenden möchten");

        int amountToSend = parseInt(handleTextInput());

        while (sampleAccount.getBalance() < amountToSend) {
            System.out.println("Sie können nicht mehr Geld versenden als Sie selbst besitzen.");

            amountToSend = parseInt(handleTextInput());
        }

        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Bitte geben Sie den Namen des Empfängers an, dem Sie " + amountToSend + "€ senden möchten");

        String recieverName = handleTextInput(); // Add this to transaction history

        transactionManager.sendMoney("-->", sampleAccount.getName(), recieverName, amountToSend);

        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Ihr Geld wurde erfolgreich versendet!");
        TimeUnit.SECONDS.sleep(1);
        menue();
    }

    public static void transaktionen() {
        clearTerminal();
        System.out.println(asciiBanner);
        transactionManager.displayTransactions();
        System.out.println("");
        manageOptions(new String[]{"Menü"});
    }

    public static void passwort_zuruecksetzen() throws Exception {
        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Bitte geben Sie Ihr altes Passwort ein.");

        String oldPassword = handleTextInput();

        boolean isAuthenticatedPassword = sampleAccount.checkPassword(oldPassword);

        while (!isAuthenticatedPassword) {
            clearTerminal();
            System.out.println(asciiBanner);
            System.out.println("Ungültiges Passwort. Bitte geben Sie Ihr altes Passwort erneut ein.");

            oldPassword = handleTextInput();

            isAuthenticatedPassword = sampleAccount.checkPassword(oldPassword);
        }

        clearTerminal();
        System.out.println(asciiBanner);
        System.out.println("Bitte geben Sie ein neues Passwort ein.");

        String newPassword = handleTextInput();

        String[] newHashedPassword = createHashedPassword(newPassword);

        sampleAccount.setNewPassword(newHashedPassword[0], newHashedPassword[1]);

        if (sampleAccount.checkPassword(newPassword)) {
            System.out.println("Passwort erfolgreich geändert!");
            TimeUnit.SECONDS.sleep(1);
            menue();
        }
    }
}
