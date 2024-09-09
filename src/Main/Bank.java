package Main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Bank {

    Scanner inputBankClass = new Scanner(System.in);

    private final ArrayList<BankAccount> bankAccountList = new ArrayList<>();

    public ArrayList<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    //User Methods:

    /**
     * A method that registers all the user's information
     * such as name, pin and Bank Account Number (BAN)
     */
    public void createBankAccount() {

        BankAccount bankAccountObj = new BankAccount();

        System.out.println("----- Welcome -----");
        System.out.println("You're about to register");

        boolean retry = true;
        while (retry) {
            System.out.print("Insert your name: ");
            String name = inputBankClass.next();
            if (checkIfContainsNumbers(name)) {
                System.out.println("Your name must contain letters. Please try again");
            } else {

                //name
                bankAccountObj.setName(name);

                //pin
                pinGenerator(bankAccountObj);
                System.out.println();
                System.out.println("This is your PIN: " + bankAccountObj.getPin());
                System.out.println("Please keep it safe");

                //bank account number (ban)
                banGenerator(bankAccountObj);
                System.out.println();
                System.out.println("This is your Bank Account Number: " + bankAccountObj.getBankAccountNumber());

                //adds user's data into the arrayList
                bankAccountList.add(bankAccountObj);
                System.out.println();
                System.out.println("BANK ACCOUNT SUCCESSFULLY CREATED");

                retry = false;

            }
        }

        boolean retry2 = true;
        while (retry2) {

            Scanner inputChoice = new Scanner(System.in);

            System.out.println();
            System.out.println("Would you like to log in now?");
            System.out.println("1) Yes");
            System.out.println("2) No");

            try {
                int choice = inputChoice.nextInt();

                retry2 = false;

                if (choice == 1) {
                    accountLogIn();

                } else {
                    System.out.println("You're returning to the main menu.");

                }

            } catch (InputMismatchException error) {
                System.out.println("Something went wrong. Please try again.");

            }

        }

    }


    /**
     * method that logs-in the user and gives access to other functions of the bank:
     * deposit, withdraw, transfer
     */
    public void accountLogIn() {

        boolean repeat = true;
        while (repeat) {

            Scanner inputPin = new Scanner(System.in);
            System.out.print("Please enter your PIN. Enter 0 to cancel: ");

            try {
                int pin = inputPin.nextInt();

                if(pin == 0) {
                    System.out.println("You're returning to the main menu.");
                    repeat = false;

                } else if (checkIfPinExist(pin)) {

                    for (BankAccount bankAccount : bankAccountList) {
                        if (bankAccount.getPin() == pin) {
                            System.out.println();
                            System.out.println("LOGGED IN SUCCESSFULLY");
                            System.out.println("BAN: " + bankAccount.getBankAccountNumber() + " | " + "Name: " + bankAccount.getName() + " | " + "Balance: €" + bankAccount.getBalance());

                            //OPERATION SELECT
                            boolean repeat2 = true;
                            while (repeat2) {

                                repeat = false;
                                System.out.println();
                                System.out.println("Please select an operation:");
                                System.out.println("1) deposit");
                                System.out.println("2) withdraw");
                                System.out.println("3) transfer");
                                System.out.println("4) cancel");

                                try {
                                    int select = inputBankClass.nextInt();

                                    switch (select) {
                                        case 1:

                                            deposit(pin);

                                            break;
                                        case 2:

                                            withdraw(pin);

                                            break;
                                        case 3:

                                            transfer(pin);

                                            break;
                                        case 4: // LOG OUT

                                            System.out.println("OPERATION CANCELED");
                                            System.out.println("You're returning to the main menu.");
                                            repeat2 = false;
                                            break;

                                        default:
                                            System.out.println("This option is invalid. Please try again.");
                                            break;

                                    }//switch

                                } catch (InputMismatchException error) {
                                    System.out.println("Something went wrong. Please try again.");
                                    break;
                                }

                            }//while repeat2
                        }
                    }

                } else if (pin < 10000 || pin > 99999) {
                    System.out.println("Your PIN must contain 5 digits. Please try again.");

                } else {
                    System.out.println("PIN is invalid. Please try again.");

                }

            } catch (InputMismatchException error) {
                System.out.println("Something went wrong. Please try again.");
            }

        }//while repeat
    }

// Operation methods:

    public void deposit(int pin) {

        boolean retry = true;
        while (retry) {

            Scanner inputDeposit = new Scanner(System.in);
            System.out.println();
            System.out.println("----- Deposit -----");
            System.out.print("Enter the amount. Enter 0 to cancel: ");

            try {
                double depositedMoney = inputDeposit.nextDouble();

                if(depositedMoney == 0) {
                    System.out.println("You're returning to the select operation menu.");
                    retry = false;

                } else if (depositedMoney < 0) {
                    System.out.println("You've probably wrote something illegal. Please try again.");

                } else {
                    retry = false;

                    for (BankAccount bankAccount : bankAccountList) {
                        if (bankAccount.getPin() == pin) {
                            double updatedBalance = bankAccount.getBalance() + depositedMoney;
                            bankAccount.setBalance(updatedBalance);
                            System.out.println();
                            System.out.println("Bank Account movements: +€" + depositedMoney);
                            System.out.println("Balance updated: " + "€" + bankAccount.getBalance());
                            System.out.println();
                            System.out.println("OPERATION WAS SUCCESSFUL");
                        }
                    }
                }
            } catch (InputMismatchException error) {
                System.out.println("Something went wrong. Please try again.");
            }
        }
    }


    public void withdraw(int pin) {

        if (checkIfBroke(pin)) {
            System.out.println("Impossible to withdraw. Your bank account is empty.");
            System.out.println("Would you like to deposit?");
            System.out.println("1) Yes");
            System.out.println("2) No");

            int choice = inputBankClass.nextInt();

            if(choice == 1) {
                deposit(pin);
                withdraw(pin);
            }

        } else {
            boolean retry = true;
            while (retry) {

                Scanner inputWithDraw = new Scanner(System.in);
                System.out.println();
                System.out.println("----- Withdraw -----");
                System.out.print("Enter the amount. Enter 0 to cancel: ");

                try {
                    double withdrawnMoney = inputWithDraw.nextDouble();

                    if(withdrawnMoney == 0) {
                        System.out.println("You're returning to the select operation menu.");
                        retry = false;

                    } else if (withdrawnMoney < 0) {
                        System.out.println("You've probably wrote something illegal. Please try again.");

                    } else {
                        for (BankAccount bankAccount : bankAccountList) {
                            if (bankAccount.getPin() == pin) {

                                if (bankAccount.getBalance() < withdrawnMoney) {
                                    System.out.println("Your request is more than what you have. Please try again.");

                                } else {
                                    retry = false;

                                    double updatedBalance = bankAccount.getBalance() - withdrawnMoney;
                                    bankAccount.setBalance(updatedBalance);
                                    System.out.println("Bank account's movement: -€" + withdrawnMoney);
                                    System.out.println("Balance updated: " + "€" + bankAccount.getBalance());
                                    System.out.println();
                                    System.out.println("OPERATION WAS SUCCESSFUL");

                                }


                            }
                        }

                    }

                } catch (InputMismatchException error) {
                    System.out.println("Something went wrong. Please try again.");
                }
            }
        }
    }


    public void transfer(int pin) {

        if (checkIfBroke(pin)) {
            System.out.println("Impossible to transfer. Your bank account is empty.");
            System.out.println("Would you like to deposit?");
            System.out.println("1) Yes");
            System.out.println("2) No");

            int choice = inputBankClass.nextInt();

            if(choice == 1) {
                deposit(pin);
                transfer(pin);
            }

        } else {
            boolean retry = true;
            while (retry) {

                Scanner inputTransfer = new Scanner(System.in);

                try {
                    System.out.println();
                    System.out.println("----- Transfer -----");
                    System.out.print("Enter receiver's Bank Account Number. Enter c to cancel: ");
                    String accountReceiversBan = inputTransfer.next();

                    if(accountReceiversBan.equals("c")) {
                        System.out.println("You're returning to the operation select menu.");
                        retry = false;

                    } else if (accountReceiversBan.length() != 12) {
                        System.out.println("A Bank Account Number must contain 12 digits. Please try again.");

                    } else if (checkIfUserInsertOwnBan(pin, accountReceiversBan)) {
                        System.out.println("You entered your own Bank Account Number. Please try again");

                    } else {
                        boolean retry2 = true;
                        while (retry2) {

                            if (checkIfBanExist(accountReceiversBan)) {
                                System.out.print("Enter the amount: ");
                                double transferredMoney = inputTransfer.nextDouble();

                                if (transferredMoney <= 0) {
                                    System.out.println("You've probably wrote something illegal. Please try again.");

                                } else {
                                    for (BankAccount bankAccount : bankAccountList) {
                                        if (bankAccount.getPin() == pin) {

                                            if (bankAccount.getBalance() < transferredMoney) {
                                                System.out.println("Your request is more than what you have. Please try again.");

                                            } else {
                                                retry = false;
                                                retry2 = false;

                                                for (BankAccount bankAccount2 : bankAccountList) {
                                                    if (bankAccount2.getBankAccountNumber().equals(accountReceiversBan)) {
                                                        double beneficiaryAccount = bankAccount2.getBalance() + transferredMoney;
                                                        bankAccount2.setBalance(beneficiaryAccount);
                                                    }
                                                }

                                                for (BankAccount bankAccount2 : bankAccountList) {
                                                    if (bankAccount2.getPin() == pin) {
                                                        double originatorAccount = bankAccount2.getBalance() - transferredMoney;
                                                        bankAccount2.setBalance(originatorAccount);
                                                        System.out.println("Bank account's movement: " + "-€" + transferredMoney);
                                                        System.out.println("Balance updated: " + "€" + bankAccount2.getBalance());
                                                        System.out.println();
                                                        System.out.println("OPERATION WAS SUCCESSFUL");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            } else {
                                System.out.println("The Bank Account Number you entered doesn't exist. Please try again.");
                                retry2 = false;
                            }

                        }//while retry2
                    }

                } catch (InputMismatchException error) {
                    System.out.println("Something went wrong. Please try again.");
                }
            }
        }
    }


//Admin Methods:

    public void adminLogIn() {

        boolean retry = true;
        while (retry) {

            Scanner inputAdminLogIn = new Scanner(System.in);
            System.out.print("Enter passcode: ");

            try {
                int adminPass = inputAdminLogIn.nextInt();

                if (adminPass == 1234) {

                    retry = false;

                    boolean repeat = true;
                    while (repeat) {

                        System.out.println();
                        System.out.println("----- ADMIN CONTROL -----");
                        System.out.println("1) Show all bank accounts");
                        System.out.println("2) Delete bank account");
                        System.out.println("3) Cancel");

                        int adminChoice = inputAdminLogIn.nextInt();

                        if (adminChoice == 1) {

                            showAllBankAccounts();

                        } else if (adminChoice == 2) {

                            deleteAccount();

                        } else if (adminChoice == 3) {

                            repeat = false;

                        }

                    }

                } else {
                    System.out.println("Wrong passcode. Please try again.");
                }

            } catch (InputMismatchException error) {
                System.out.println("Passcodes only contain numbers. Try again");
            }

        }

    }


    private void deleteAccount() {

        if(bankAccountList.isEmpty()) {
            System.out.println("The bank is empty.");

        } else {
            System.out.print("Enter the account's index: ");

            try {
                int index = inputBankClass.nextInt() - 1;
                bankAccountList.remove(index);
                System.out.println("THE BANK ACCOUNT IN INDEX " + (index + 1) + " HAS BEEN DELETED");

            } catch(InputMismatchException error) {
                System.out.println("Something went wrong.");
            }

        }

    }


    private void showAllBankAccounts() {

        if(bankAccountList.isEmpty()) {
            System.out.println("The Bank is empty.");

        } else {
            int i = 0;
            for (BankAccount bankAccount : bankAccountList) {
                i++;
                System.out.print("Account n*" + i + " | ");
                System.out.println("PIN: " + bankAccount.getPin() + " | " + "BAN: " + bankAccount.getBankAccountNumber() + " | " + "Name: " + bankAccount.getName() + " | " + "Balance: €" + bankAccount.getBalance() + 0);

            }
        }
    }

    //Random numbers generator

    /**
     * real banks provide the user's Bank Account Number (BAN):
     * this method creates random numbers to make a Bank Account Number
     * whenever a bank account is created
     *
     * @param bankAccountParam need to invoke BankAccount class to invoke setBankAccountNumber method
     */
    private static void banGenerator(BankAccount bankAccountParam) {
        Random randomBan = new Random();
        String first6Digits = "000000";
        int randomNum = 0;

        while (randomNum < 100000) {
            randomNum = randomBan.nextInt(999999);
            String second6Digits = Integer.toString(randomNum);
            bankAccountParam.setBankAccountNumber(first6Digits + second6Digits);
        }

    }


    /**
     * real banks provide the user's Personal Identification Number (PIN):
     * this method creates random numbers to make a PIN whenever a bank account is created
     *
     * @param bankAccountParam need to invoke BankAccount class to invoke setPin method
     */
    private static void pinGenerator(BankAccount bankAccountParam) {
        Random random = new Random();
        int randomPin = 0;

        while (randomPin < 10000) {
            randomPin = random.nextInt(99999);
            bankAccountParam.setPin(randomPin);
        }

    }


    /**
     * This method prevents the user to input random numbers
     *
     * @param input a String which the method checks if it contains numbers
     * @return a boolean, if false it rejects the user's input
     */
    private static boolean checkIfContainsNumbers(String input) {
        return input.matches(".*\\d+.*");
    }

    // the regex pattern: A Regular Expression (or Regex) is a pattern (or filter) that describes a set of strings
    // that matches the pattern. In other words, a regex accepts a certain set of strings and rejects the rest.


    /**
     * This method prevents the user to access the withdrawal or transfer function
     * if their bank account is empty or equal to 0
     *
     * @param pin requires logged-in user's pin
     * @return a boolean. If true (that he or she is broke) then they cannot withdraw nor transfer money
     */
    private boolean checkIfBroke(int pin) {

        boolean check = false;

        for (BankAccount bankAccount : bankAccountList) {
            if (bankAccount.getPin() == pin) {

                if (bankAccount.getBalance() == 0) {
                    check = true;
                }

            }
        }

        return check;
    }


    /**
     * This method checks if the pin inserted exists in the arraylist
     *
     * @param pin accepts an integer
     * @return true if the pin really exists
     */
    private boolean checkIfPinExist(int pin) {

        boolean userPin = false;

        for (BankAccount b : bankAccountList) {
            if (b.getPin() == pin) {
                userPin = true;
                break;
            }
        }

        return userPin;

    }

    /**
     * This method checks if the BAN inserted exists in the arraylist
     * @param ban accepts a String
     * @return true if BAN really exists
     */
    private boolean checkIfBanExist(String ban) {

        boolean receiverBan = false;

        for (BankAccount b : bankAccountList) {
            if (b.getBankAccountNumber().equals(ban)) {
                receiverBan = true;
                break;
            }
        }

        return receiverBan;

    }

    /**
     * This method prevents the user for inputting their own BAN in the transfer section
     * @param pin requires user's pin
     * @param ban requires user's ban
     * @return a boolean. If true the program won't continue
     */
    private boolean checkIfUserInsertOwnBan(int pin, String ban) {

        boolean sameUserBan = false;

        for(BankAccount bankAccount : bankAccountList) {
            if(bankAccount.getPin() == pin) {
                if(bankAccount.getBankAccountNumber().equals(ban)) {
                    sameUserBan = true;
                }
            }
        }

        return sameUserBan;

    }

}//class

