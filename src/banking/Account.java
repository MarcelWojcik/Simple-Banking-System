package banking;



import java.lang.StringBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;

import static banking.Main.dataSource;

public class Account {
    private final static String BIN = "400000";
    private String cardNumber;
    private String pin;
    private int balance;
    public static HashMap<String, Account> Cards = new HashMap<>();


    Account() throws SQLException {

        this.pin = generateSequence(4);
        this.balance = 0;

        String sequence;
        do {
            sequence = this.BIN + generateSequence(9);
            sequence = sequence + generateLastDigit(sequence);
        } while(Cards.containsKey(sequence));
        this.cardNumber = sequence;

        Cards.put(this.cardNumber, this);


        try(Connection conn = dataSource.getConnection()){

            try(Statement statement = conn.createStatement()) {
                statement.executeUpdate(String.format( """
                            INSERT INTO card (number, pin) VALUES ("%s", "%s");
                            """, this.cardNumber, this.pin)
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

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
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateLastDigit(String cardNumber) {
        int result = 0;

        for (int i = 1; i <= cardNumber.length(); i++) {
            int num = Integer.parseInt(cardNumber.substring(i - 1, i));
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
