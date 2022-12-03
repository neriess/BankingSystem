package bankingsystem;

public class Account {
    private final long cardNumber;
    private final int PIN;
    private int balance;

    public Account(long cardNumber, int PIN, int balance) {
        this.cardNumber = cardNumber;
        this.PIN = PIN;
        this.balance = balance;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getPIN() {
        return PIN;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
