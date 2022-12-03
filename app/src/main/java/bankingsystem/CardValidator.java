package bankingsystem;

public class CardValidator {

    public static boolean isCardValid(Database database, long cardNumToCheck, long loggedCardNum) {

        if (cardNumToCheck == loggedCardNum) {
            System.out.printf("%nYou can't transfer money to the same account!%n%n");
            return false;
        } else if (!isCardValidByLuhnAlgorithm(cardNumToCheck)) {
            System.out.printf("%nProbably you made a mistake in the card number. " +
                    "Please try again!%n%n");
            return false;
        } else if (!database.isCardInDatabase(cardNumToCheck)) {
            System.out.printf("%nSuch a card does not exist.%n%n");
            return false;
        }

        return true;
    }

    private static boolean isCardValidByLuhnAlgorithm(long cardNumToCheck) {

        int[] numbers = Long.toString(cardNumToCheck).chars().map(c -> c-'0').toArray();

        for (int i = 0; i < numbers.length; i += 2) {
            int num = numbers[i] * 2;
            if (num > 9) {
                num -= 9;
            }
            numbers[i] = num;
        }

        int sum = 0;

        for (int num : numbers) {
            sum += num;
        }

        return sum % 10 == 0;
    }
}
