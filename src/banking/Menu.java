package banking;

import java.sql.SQLOutput;
import java.util.Scanner;


public class Menu {

    private static boolean running = true;
    static Scanner scanner = new Scanner(System.in);
    static Account logged = null;

    public static void start(){
        while(running) {
            displayMainMenu();
            handleInput();
        }
        scanner.close();
    }

    static void handleInput(){

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.println();
            switch(choice){
                case 1: {
                    if(logged == null) {
                        Actions.createAccount();
                    } else {
                        Actions.printBalance(logged);
                    }

                    break;
                }

                case 2:
                    if(logged == null) {
                        System.out.println("Enter your card number: ");
                        String cardNumber = scanner.nextLine();
                        System.out.println("Enter your PIN: ");
                        String pin = scanner.nextLine();
                        logged = Actions.login(cardNumber, pin);
                    }else {
                        System.out.println("Enter income:");
                        int income = Integer.parseInt(scanner.nextLine());

                        Actions.addIncome(income, logged);
                    }
                    break;


                case 3:
                    if(logged != null) {
                        System.out.println("Transfer");
                        System.out.println("Enter card number:");
                        String cardNumber = scanner.nextLine();
                        if(Util.cardNumberIsValid(cardNumber)) {
                        } else {
                            System.out.println("Probably you made a mistake in the card number. Please try again!");
                            break;
                        }
                        if(logged.getCardNumber().equals(cardNumber)) {
                            System.out.println("You can't transfer money to the same account!");
                            break;
                        }
                        if(Actions.cardExists(cardNumber)) {
                            System.out.println("Enter how much money you want to transfer:");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            Actions.transfer(logged, cardNumber, amount);


                        }else {
                            System.out.println("Such a card does not exist.");
                        }
                        break;
                    }

                case 5:
                    if(logged != null) {
                        logged = null;
                        System.out.println("You have successfully logged out");
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    running = false;
                    return;
                default:
                    throw new Exception("Invalid input");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }


    static void displayMainMenu(){
        if(logged == null) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
        }else {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
        }

    }
}
