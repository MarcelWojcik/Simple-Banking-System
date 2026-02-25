package banking;


public class Actions {


    public static void createAccount(){
        try {
            Account newAccount = new Account();
            System.out.println("Your card has been created");
            System.out.println("Your card number:\n" + newAccount.getCardNumber());
            System.out.println("Your card PIN:\n" + newAccount.getPin());
            System.out.println("");

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static Account login(String cardNumber, String pin){

        if(Account.Cards.containsKey(cardNumber) && Account.Cards.get(cardNumber).getPin().equals(pin)){
            System.out.println("You have successfully logged in!");
            return Account.Cards.get(cardNumber);
        } else {
            System.out.println("Wrong card number or PIN!");
            return null;
        }
    }

}
