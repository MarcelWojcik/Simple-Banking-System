package banking;


public class Account {
    public final static String BIN = "400000";
    private String cardNumber;
    private int balance;


    Account(String cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;

    }


    public String getCardNumber() {
        return cardNumber;
    }


    public int getBalance() {
        return balance;
    }

}
