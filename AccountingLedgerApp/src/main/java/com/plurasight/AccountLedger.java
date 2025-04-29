package com.plurasight;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class AccountLedger {
    /*
§ D) Add Deposit - prompt user for the deposit information and
save it to the csv file

§ P) Make Payment (Debit) - prompt user for the debit
information and save it to the csv file

     */
    private Scanner scanner = new Scanner(System.in);
    private List<Transaction> transactionList = new ArrayList<>();


    public void homeScreen() {
        loadTransactionInfo();

        System.out.println("\nWelcome, please input one of the following: " +
                "\n\tD) Add Deposit" +
                "\n\tP) Make Payment (Debit)" +
                "\n\tL) Ledger" +
                "\n\tX) Exit");

        int userInputHome = 0;
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("d")) {
            userInputHome = 1;
        } else if (userInput.equalsIgnoreCase("p")) {
            userInputHome = 2;
        } else if (userInput.equalsIgnoreCase("l")) {
            userInputHome = 3;
        } else if (userInput.equalsIgnoreCase("x")) {
            userInputHome = 4;
        } else {
            System.out.println("user input was not one of the options");
        }

        switch (userInputHome) {
            case 1:
                System.out.println("working on addDeposit method");

                break;
            case 2:
                System.out.println("working on makePayment method");
                break;
            case 3:
                ledger();
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("Please try again - home screen");
                homeScreen();
        }
    }//end of homeScreen method

    public void loadTransactionInfo() {                                                                          //method to load Transaction info from .csv file onto ArrayList

        try {
            System.out.println("Loading Account Information");

            BufferedReader bufReader = new BufferedReader(new FileReader("transactions.csv"));     //BufferedReader variable that takes a FileReader as arguement that takes a .csv file arguement
            String FileInput;                                                                   //String Variable to hold transaction info

            bufReader.readLine();                                                                       //skip the first line, assumes that the first line is headers and garbage data

            while ((FileInput = bufReader.readLine()) != null) {                                //in the midst of while loop read a line from .csv file and load it onto String Variable and check if it comes out null
                String[] tokens = FileInput.split(Pattern.quote("|"));                   //load the line onto a String array so that it can be partitioned by the pattern "|"
                Transaction transaction = new Transaction();                                                 //create an empty Transaction object
                if (tokens.length == 5) {
                    transaction.setRecordedDate(LocalDate.parse(tokens[0]));
                    transaction.setRecordedTime(LocalTime.parse(tokens[1]));
                    transaction.setDescription(tokens[2]);
                    transaction.setVendor(tokens[3]);
                    transaction.setAmount(Double.parseDouble(tokens[4]));//will load and set all transaction information only if there is exactly 5 elements in the String Array
                } else {
                    System.out.println("error: missing or too much information on a given transaction");
                }
                transactionList.add(transaction);                                                         //load the Employee object onto the ArrayList for Employee
            }
            bufReader.close();                                                                          //bufferedReader close

        } catch (
                IOException e) {                                                                       //in case of an error with I/O
            System.out.println("error with .csv file naming, please check if its the correct save file");                              //catch to loop back to beginning

        }
    }

    public void ledger() {
        System.out.println("\nLedger Display Screen, please input one of the following: " +
                "\n\tA) All entries" +
                "\n\tD) Deposits" +
                "\n\tP) Payments" +
                "\n\tR) Reports" +
                "\n\tH) Home");

        int userInputLedger = 0;
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("a")) {
            userInputLedger = 1;
        } else if (userInput.equalsIgnoreCase("d")) {
            userInputLedger = 2;
        } else if (userInput.equalsIgnoreCase("p")) {
            userInputLedger = 3;
        } else if (userInput.equalsIgnoreCase("r")) {
            userInputLedger = 4;
        } else if (userInput.equalsIgnoreCase("h")) {
            userInputLedger = 5;
        } else {
            System.out.println("user input was not one of the options");
        }

        switch (userInputLedger) {
            case 1:
                displayAll();
            case 2:
                displayDepositOnly();
            case 3:
                displayPaymentsOnly();
            case 4:
                reports();
            case 5:
                System.out.println("Returning to Home Page");
                homeScreen();
            default:
                System.out.println("Please try again - ledger");
                ledger();
        }
    }

    public void displayAll() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        System.out.println("Printing All Account Transaction Information:");
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            String formatDate = transaction.getRecordedDate().format(dateFormatter);
            String formatTime = transaction.getRecordedTime().format(timeFormatter);
            System.out.printf("Date and Time: %s : %s |\t Descripton: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
        }
        ledger();
    }

    public void displayDepositOnly(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        System.out.println("Printing All Deposits Information:");
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if(transaction.getAmount() > 0) {
                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Descripton: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        ledger();
    }

    public void displayPaymentsOnly(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        System.out.println("Printing All Payments Information:");
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if(transaction.getAmount() < 0) {
                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        ledger();
    }

     /*

in-depth reports screen
§ 1) Month To Date
§ 2) Previous Month
§ 3) Year To Date
§ 4) Previous Year
§ 5) Search by Vendor - prompt the user for the vendor name
and display all entries for that vendor
§ 0) Back - go back to the report page
o H) Home - go back to the home page
     */

    public void reports() {
        System.out.println("\nReports Display Screen, please input one of the following: " +
                "\n\t1) Month To Date" +
                "\n\t2) Previous Month" +
                "\n\t3) Year To Date" +
                "\n\t4) Previous Year" +
                "\n\t5) Search by Vendor" +
                "\n\t0) Back to ledger");
        try {
            int userInputReport = scanner.nextInt();
            scanner.nextLine();

            switch (userInputReport) {
                case 0:
                    ledger();
                case 1:
                    System.out.println("working on displayMonthToDate method");
                    displayMonthToDate();
                    break;
                case 2:
                    System.out.println("working on displayPreviousMonth method");
                    break;
                case 3:
                    System.out.println("working on displayYearToDate method");
                    break;
                case 4:
                    System.out.println("working on displayPreviousYear method");
                    break;
                case 5:
                    System.out.println("working on searchByVendor method");
                default:
                    System.out.println("Input was not one of the options. Please try again");
                    reports();
            }
        } catch (InputMismatchException e) {
            System.out.println("A non-number was inputted please try again");
            String eater = scanner.nextLine();
            reports();
        }
    }

    public void displayMonthToDate(){
        LocalDate toThisDate = LocalDate.now();
        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formatToThisDate = toThisDate.format(dateFormatter);
        String formatThisMonth = thisMonth.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatThisMonth + " to " + formatToThisDate);
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if((transaction.getRecordedDate().isAfter(thisMonth) && (transaction.getRecordedDate().isBefore(toThisDate))  ) ){

                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }


    }




    public void exit() {
        System.out.println("Exiting the application, please come again");
        System.exit(0);
    }

}