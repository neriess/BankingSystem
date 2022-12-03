package bankingsystem;

import java.util.Random;

public class CardGenerator {

    //this cardNumber can pass Luhn algorithm
    public static long generateCardNumber() {

        String BIN = "400000";
        StringBuilder stb = new StringBuilder(BIN);

        for (int i = 0; i < 9; i++) {
            stb.append(new Random().nextInt(10));
        }

        String[] arr = stb.toString().split("");

        for (int i = 0; i < stb.length(); i += 2) {
            int num = Integer.parseInt(arr[i]) * 2;
            if (num > 9) {
                num -= 9;
            }
            arr[i] = String.valueOf(num);
        }

        int sum = 0;

        for (String s : arr) {
            sum += Integer.parseInt(s);
        }

        int reminder = sum%10;

        if (reminder == 0) {
            stb.append(0);
        } else {
            stb.append(10 - reminder);
        }

        return Long.parseLong(stb.toString());
    }

    public static int generateCardPIN() {
        return new Random().nextInt(1000, 10000);
    }
}
