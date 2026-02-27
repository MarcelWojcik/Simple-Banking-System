package banking;

import java.sql.*;


import static banking.Main.dataSource;

public class Actions {


    public static void createAccount(){

        try(Connection conn = dataSource.getConnection()){

            String pin = Util.generateSequence(4);
            String cardNumber;




            try(Statement statement = conn.createStatement()) {


                do {
                    cardNumber = Account.BIN + Util.generateSequence(9);
                    cardNumber = cardNumber + Util.generateLastDigit(cardNumber);

                } while(statement.executeQuery(String.format("SELECT  number FROM card where number = '%s'", cardNumber)).next());

                statement.executeUpdate(String.format( """
                            INSERT INTO card (number, pin) VALUES ("%s", "%s");
                            """, cardNumber, pin)
                );

                System.out.println("Your card has been created");
                System.out.println("Your card number:\n" + cardNumber);
                System.out.println("Your card PIN:\n" + pin);
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public static Account login(String cardNumber, String pin){

        String getAccountSQL = String.format("""
                                SELECT * FROM card WHERE number = ? AND pin = ?
                                """);


        try(Connection conn = dataSource.getConnection()){

            try(PreparedStatement statement = conn.prepareStatement(getAccountSQL)) {

                statement.setString(1, cardNumber);
                statement.setString(2, pin);
                ResultSet data = statement.executeQuery();

                if(data.next()){
                    System.out.println("You have successfully logged in!");
                    return new Account(data.getString("number"), data.getInt("balance"));
                } else {
                    System.out.println("Wrong card number or PIN!");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void printBalance(Account account){

        String getBalanceSQL = String.format("""
                                SELECT balance FROM card WHERE number = ?
                                """);

        try(Connection con = dataSource.getConnection()){
            try(PreparedStatement statement = con.prepareStatement(getBalanceSQL)) {
                statement.setString(1, account.getCardNumber());
                ResultSet data = statement.executeQuery();
                account.setBalance(data.getInt("balance"));
            }

        } catch( SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Balance: " + account.getBalance());

    }

    public static void addIncome(int income, Account logged){

        String addIncomeSQL = """
                        UPDATE card SET balance = balance + ? WHERE number = ?;
                """;

        try(Connection con = dataSource.getConnection()){
            try(PreparedStatement stm = con.prepareStatement(addIncomeSQL)){
                stm.setInt(1, income);
                stm.setString(2, logged.getCardNumber());
                stm.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Added income: " + income);

    }

}
