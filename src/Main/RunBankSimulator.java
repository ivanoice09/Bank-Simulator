package Main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RunBankSimulator {

    public static void main(String[] args) {

        System.out.println("----- BANK ACCOUNT SIMULATOR -----");

        Bank bank = new Bank();

        boolean mainMenuLoop = true;
        while (mainMenuLoop) {

            Scanner inputTestBank = new Scanner(System.in);

            System.out.println();
            System.out.println("----- MAIN MENU -----");
            System.out.println("1) Log in");
            System.out.println("2) Open a bank account");
            System.out.println("3) Admin control");

            try {
                int choice = inputTestBank.nextInt();

                switch (choice) {

                    case 1: // LOG IN

                        if (bank.getBankAccountList().isEmpty()) {
                            System.out.println("No account is registered in this bank yet.");
                            System.out.println("Would like to open a bank account?");
                            System.out.println("1) Yes");
                            System.out.println("2) No");

                            int choice2 = inputTestBank.nextInt();

                            if (choice2 == 1) {

                                bank.createBankAccount();

                            } else {
                                System.out.println("You're returning to the main menu.");

                            }

                        } else {

                            bank.accountLogIn();

                        }

                        break;
                    case 2: // CREATE ACCOUNT

                        bank.createBankAccount();

                        break;
                    case 3: // ADMIN CONTROL

                        bank.adminLogIn();

                        break;
                    case 4: // DEMOLISH THE BANK

                        System.out.println("BOOM!");
                        mainMenuLoop = false;
                        break;

                    default:
                        System.out.println("Choice is invalid. Please try again.");
                        break;

                }

            } catch (InputMismatchException error) {
                System.out.println("Something went wrong. Please try again.");
            }

        }//while: mainMenuLoop


    }//main

}//class
