package bankingsystem;

import java.util.Scanner;

public class AccManager {

    Account loggedAccount;

    public void createAnAccount(Database database) {

        long cardNumber;
        int cardPIN;
        int initialBalance = 0;
        Account account;

        cardNumber = CardGenerator.generateCardNumber();
        cardPIN = CardGenerator.generateCardPIN();

        account = new Account(cardNumber, cardPIN, initialBalance);
        database.insert(account);

        System.out.printf("%nYour card has been created%n");
        System.out.println(accountInfo(account));
    }

    private String accountInfo(Account account) {
        return String.format("""
                Your card number:
                %d
                Your card PIN:
                %d%n""", account.getCardNumber(), account.getPIN());
    }

    public void logIntoAccount(Scanner scn, Database database) {

        long cardNumber;
        int cardPIN;

        cardNumber = getCardNumberFromUser(scn);
        cardPIN = getCardPinFromUser(scn);

        loggedAccount = database.findAccountInDatabase(cardNumber, cardPIN);

        if (loggedAccount != null) {
            System.out.printf("%nYou have successfully logged in!%n%n");
        }
    }

    private long getCardNumberFromUser(Scanner scn) {
        System.out.printf("%nEnter your card number:%n");
        return scn.nextLong();
    }

    private int getCardPinFromUser(Scanner scn) {
        System.out.println("Enter your PIN:");
        return scn.nextInt();
    }

    public void showBalance() {
        System.out.printf("%nBalance: %d%n%n", loggedAccount.getBalance());
    }

    public void logOut() {
        loggedAccount = null;
        System.out.printf("%nYou have successfully logged out!%n%n");
    }

    public void addIncome(Scanner scn, Database database) {

        int amount;
        int accBalance = loggedAccount.getBalance();
        long accNumber = loggedAccount.getCardNumber();

        System.out.printf("%nEnter income:%n");
        amount = scn.nextInt();

        loggedAccount.setBalance(accBalance + amount);
        database.updateBalanceOfAccount(accNumber, amount, true);

        System.out.printf("%nIncome was added!%n%n");
    }

    public void closeAccount(Database database) {

        database.deleteAccount(loggedAccount);
        loggedAccount = null;

        System.out.printf("%nThe account has been closed!%n%n");
    }

    public void doTransfer(Scanner scn, Database database) {

        long cardNum;
        int amount;
        int accountBalance = loggedAccount.getBalance();
        long loggedCardNum= loggedAccount.getCardNumber();

        System.out.printf("%nTransfer%nEnter card number:%n");
        cardNum = scn.nextLong();

        boolean isCardValid = CardValidator.isCardValid(database,
                cardNum, loggedAccount.getCardNumber());

        if (isCardValid) {
            System.out.printf("%nEnter how much money you want to transfer:%n");
            amount = scn.nextInt();

            if ((accountBalance - amount) < 0) {
                System.out.printf("%nNot enough money!%n%n");
            } else {
                makeTransfer(database, loggedCardNum, cardNum, amount);
                System.out.printf("%nSuccess!%n%n");
            }
        }
    }

    private void makeTransfer(Database database, long fromCardNumber, long toCardNumber, int amount) {

        database.updateBalanceOfAccount(fromCardNumber, amount, false);
        database.updateBalanceOfAccount(toCardNumber, amount, true);
    }
}
