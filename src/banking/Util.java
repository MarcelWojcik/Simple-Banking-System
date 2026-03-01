package banking;

import java.util.Random;


public class Util {

    public static boolean cardNumberIsValid(String cardNumber){
        String cardPrefix = cardNumber.substring(0, cardNumber.length() - 1);
        String lastDigit = cardNumber.substring(cardNumber.length() - 1);
        return cardNumber.length() == 16 && cardNumber.startsWith(Account.BIN) && Integer.parseInt(lastDigit) == Integer.parseInt(generateLastDigit(cardPrefix));
    }

    /**
     * @param n number of digits to generate
     *
     * @return String of {@code n} generated sequence of digits
     * */
    public static String generateSequence(int n){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * @param cardNumberPrefix  digits for calculating the last digit using Luhn alhorithm
     *
     * @return last digit of the card number
     *
     * */
    public static String generateLastDigit(String cardNumberPrefix) {
        int result = 0;

        for (int i = 1; i <= cardNumberPrefix.length(); i++) {
            int num = Integer.parseInt(cardNumberPrefix.substring(i - 1, i));
            if (i % 2 != 0) {
                num *= 2;
                if (num > 9) num -= 9;
            }
            result += num;
        }
        result = result % 10 == 0 ? 0 : 10 - result % 10;

        return "" + result;
    }
}
