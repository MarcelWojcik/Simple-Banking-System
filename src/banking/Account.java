package banking;


import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Random;

public class Account {
    private final static String BIN = "400000";
    private String cardNumber;
    private String pin;
    private int balance;
    public static HashMap<String, Account> Cards = new HashMap<>();


    Account() {

        this.pin = generateSequence(4);
        this.balance = 0;
        do{
            this.cardNumber = this.BIN + generateSequence(10);
        } while(Cards.containsKey(cardNumber));
        Cards.put(cardNumber, this);

    }



    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public static String generateSequence(int n){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

}
