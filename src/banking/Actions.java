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
                System.out.println("");
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
                                """, cardNumber, pin);


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


}
