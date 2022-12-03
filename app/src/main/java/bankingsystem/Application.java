package bankingsystem;

import java.util.Scanner;

public class Application {
    Scanner scn;
    AccManager accManager;
    Database database;
    private final String MAIN_MENU = """
            1. Create an account
            2. Log into account
            0. Exit""";
    private final String ACCOUNT_MENU = """
            1. Balance
            2. Add income
            3. Do transfer
            4. Close account
            5. Log out
            0. Exit""";
    private final String WRONG_ACTION = "\nUnsuitable action, please, try again\n";

    public Application(String fileName) {
        this.scn = new Scanner(System.in);
        this.accManager = new AccManager();
        this.database = new Database(fileName);
    }


    public void run() {

        while (true) {

            System.out.println(MAIN_MENU);

            switch (getMenuInputFromUser()) {
                case 0 -> exit();
                case 1 -> accManager.createAnAccount(database);
                case 2 -> {
                    accManager.logIntoAccount(scn, database);
                    runAccountMenu();
                }
                default -> System.out.println(WRONG_ACTION);
            }
        }
    }

    private void runAccountMenu() {

        boolean goBack = false;

        while(!goBack) {

            System.out.println(ACCOUNT_MENU);

            switch (getMenuInputFromUser()) {
                case 0 -> exit();
                case 1 -> accManager.showBalance();
                case 2 -> accManager.addIncome(scn, database);
                case 3 -> accManager.doTransfer(scn, database);
                case 4 -> {
                    accManager.closeAccount(database);
                    goBack = true;
                }
                case 5 -> {
                    accManager.logOut();
                    goBack = true;
                }
            }
        }
    }

    private void exit() {
        System.out.printf("%nBye!");
        System.exit(0);
    }

    private int getMenuInputFromUser() {
        return scn.nextInt();
    }
}
