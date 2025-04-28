package com.plurasight;

import java.time.*;
import java.util.Scanner;


public class AccountLedger {
    /*
                      Home Screen

§ D) Add Deposit - prompt user for the deposit information and
save it to the csv file

§ P) Make Payment (Debit) - prompt user for the debit
information and save it to the csv file

§ L) Ledger - display the ledger screen

§ X) Exit - exit the application

     */
    private Scanner scanner = new Scanner(System.in);

    public void homeScreen(){

        System.out.println("D) Add Deposit" +
                "\nP) Make Payment (Debit)" +
                "\nL) Ledger" +
                "\nX) Exit");
        
        int userInputConverted = 0;
        String userInput = scanner.nextLine();
        if(userInput.equalsIgnoreCase("d")){
            userInputConverted = 1;
        }
        else if (userInput.equalsIgnoreCase("p")){
            userInputConverted = 2;
        }
        else if (userInput.equalsIgnoreCase("l")){
            userInputConverted = 3;
        } else if (userInput.equalsIgnoreCase("x")) {
            userInputConverted = 4;
        }
        else{
            System.out.println("user input was not one of the options");
        }
        
        switch (userInputConverted){
            case 1:
                System.out.println("working on addDeposit method");
                break;
            case 2:
                System.out.println("working on makePayment method");
                break;
            case 3:
                System.out.println("working on ledger method");
                break;
            case 4:
                System.out.println("Exiting the application, please come again");
                break;
            default:
                System.out.println("Please try again");
                homeScreen();
        }


    }

















     /*

in-depth detail on Ledger menu
 Ledger - All entries should show the newest entries first

o A) All - Display all entries
o D) Deposits - Display only the entries that are deposits into the
account
o P) Payments - Display only the negative entries (or payments)
o R) Reports - A new screen that allows the user to run pre-defined
reports or to run a custom search

in-depth reports
§ 1) Month To Date
§ 2) Previous Month
§ 3) Year To Date
§ 4) Previous Year
§ 5) Search by Vendor - prompt the user for the vendor name
and display all entries for that vendor
§ 0) Back - go back to the report page
o H) Home - go back to the home page
     */
}
