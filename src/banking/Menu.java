package banking;

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
            System.out.println("");
            switch(choice){
                case 1: {
                    if(logged == null) {
                        Actions.createAccount();
                    } else {
                        System.out.println("Balance: " + logged.getBalance());
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
        System.out.println("");

    }


    static void displayMainMenu(){
        if(logged == null) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
        }else {
            System.out.println("1. Balance");
            System.out.println("2. Log out");
            System.out.println("0. Exit");
        }

    }


}
