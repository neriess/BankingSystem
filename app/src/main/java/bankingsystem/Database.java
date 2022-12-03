package bankingsystem;

import java.sql.*;

public class Database {

    String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
        createTable();
    }

    public Connection connect() {

        String url = "jdbc:sqlite:" + fileName;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    private void createTable() {

        try (Statement statement = connect().createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Account account) {
        String sql = "INSERT INTO CARD(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement prepared = conn.prepareStatement(sql)) {
            prepared.setString(1, Long.toString(account.getCardNumber()));
            prepared.setString(2, Integer.toString(account.getPIN()));
            prepared.setInt(3, account.getBalance());

            prepared.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Account findAccountInDatabase(long cardNumber, int cardPIN) {
        String sql = "SELECT * FROM CARD";

        try (Statement statement = connect().createStatement();
             ResultSet card = statement.executeQuery(sql)) {

            while (card.next()) {
                long number = Long.parseLong(card.getString("number"));
                int pin = Integer.parseInt(card.getString("pin"));
                int balance = card.getInt("balance");

                if (number == cardNumber && pin == cardPIN) {
                    return new Account(number, pin, balance);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf("%nWrong card number or PIN!%n%n");
        return null;
    }

    public void updateBalanceOfAccount(long accNumber, int amount, boolean addToBalance) {

        String sql;

        if (addToBalance) {
            sql = "UPDATE card SET balance = balance + ?"
                    + "WHERE number = ?";
        } else {
            sql = "UPDATE card SET balance = balance - ?"
                    + "WHERE number = ?";
        }


        try (Connection conn = this.connect();
             PreparedStatement prepared = conn.prepareStatement(sql)) {

            prepared.setInt(1, amount);
            prepared.setString(2, Long.toString(accNumber));

            prepared.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(Account account) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = this.connect();
             PreparedStatement prepared = conn.prepareStatement(sql)) {

            prepared.setString(1, Long.toString(account.getCardNumber()));

            prepared.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isCardInDatabase(long cardNumToCheck) {
        String sql = "SELECT * FROM CARD";

        try (Statement statement = connect().createStatement();
             ResultSet card = statement.executeQuery(sql)) {

            while (card.next()) {
                long number = Long.parseLong(card.getString("number"));

                if (number == cardNumToCheck) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
