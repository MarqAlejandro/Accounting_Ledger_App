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
§ P) Make Payment (Debit) - prompt user for the debit
information and save it to the csv file

     */
    private Scanner scanner = new Scanner(System.in);
    private List<Transaction> transactionList = new ArrayList<>();


    public void homeScreen() {
        transactionList.clear();
        loadTransactionInfo();
        sortNewest();

        System.out.print("""
                
                Welcome, please input one of the following: \
                
                \tD) Add Deposit\
                
                \tP) Make Payment (Debit)\
                
                \tL) Ledger\
                
                \tX) Exit\
                
                Enter:\s""");

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
                addDeposit();
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
                System.out.println("Please try again");
                homeScreen();
        }
    }//end of homeScreen method

    public void addDeposit() {
        try {
        System.out.print("Enter Transaction Description: ");
        String transactionDescription = scanner.nextLine();
        System.out.print("Enter Transaction Vendor: ");
        String transactionVendor = scanner.nextLine();
        System.out.print("Enter Transaction Amount: ");
        double transactionAmount = scanner.nextDouble();
        scanner.nextLine();

        Transaction transaction = new Transaction(transactionDescription, transactionVendor, transactionAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalTime formatTime = LocalTime.parse(transaction.getRecordedTime().format(formatter));


        FileWriter writer = new FileWriter("transactions.csv",true);


        writer.write(transaction.getRecordedDate() + "|" + formatTime + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount());
        writer.write("\n");
        writer.close();

        homeScreen();
        } catch (InputMismatchException eInput) {
            System.out.println("Amount input was non-numeric, please try again");
            scanner.nextLine();
            addDeposit();
        } catch (IOException eIO) {
            System.out.println("error with .csv file naming, please check if its the correct save file");
        }
    }

    public void loadTransactionInfo() {                                                                          //method to load Transaction info from .csv file onto ArrayList

        try {
            //System.out.println("Loading Account Information");

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
                    transaction.setRecordedDateTime(transaction.getRecordedDate(), transaction.getRecordedTime());
                } else {
                    System.out.println("error: missing or too much information on a given transaction");
                }
                transactionList.add(transaction);                                                         //load the Employee object onto the ArrayList for Employee
            }
            bufReader.close();                                                                          //bufferedReader close

        } catch (IOException e) {                                                                       //in case of an error with I/O
            System.out.println("error with .csv file naming, please check if its the correct save file");                              //catch to loop back to beginning

        }
    }

    public void sortNewest(){
        transactionList.sort((transaction1, transaction2) -> transaction2.getRecordedDateTime().compareTo(transaction1.getRecordedDateTime()));
    }

    public void ledger() {
        System.out.print("""
                
                Ledger Display Screen, please input one of the following: \
                
                \tA) All entries\
                
                \tD) Deposits\
                
                \tP) Payments\
                
                \tR) Reports\
                
                \tH) Home\
                
                Enter:\s""");

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
                System.out.println("Returning to Home Page...");
                homeScreen();
            default:
                System.out.println("Please try again");
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
        System.out.println("\nReturning to Ledger Display Screen...");
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
        System.out.println("\nReturning to Ledger Display Screen...");
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
        System.out.println("Returning to Ledger Display Screen...");
        ledger();
    }

    public void reports() {
        System.out.print("""
                
                Reports Display Screen, please input one of the following: \
                
                \t1) Month To Date\
                
                \t2) Previous Month\
                
                \t3) Year To Date\
                
                \t4) Previous Year\
                
                \t5) Search by Vendor\
                
                \t0) Back to ledger\
                
                Enter:\s""");
        try {
            int userInputReport = scanner.nextInt();
            scanner.nextLine();

            switch (userInputReport) {
                case 0:
                    ledger();
                case 1:
                    displayMonthToDate();
                    break;
                case 2:
                    displayPreviousMonth();
                    break;
                case 3:
                    displayYearToDate();
                    break;
                case 4:
                    displayPreviousYear();
                    break;
                case 5:
                    searchByVendor();
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
        String formatBeginningThisMonth = thisMonth.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningThisMonth + " to " + formatToThisDate);
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if((transaction.getRecordedDate().isAfter(thisMonth) && (transaction.getRecordedDate().isBefore(toThisDate))  ) ){

                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void displayPreviousMonth(){
        LocalDate beginningLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.MAX.getDayOfMonth());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formatBeginningLastMonth = beginningLastMonth.format(dateFormatter);
        String formatEndLastMonth = endLastMonth.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningLastMonth + " to " + formatEndLastMonth);
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if((transaction.getRecordedDate().isAfter(beginningLastMonth) && (transaction.getRecordedDate().isBefore(endLastMonth))  ) ){

                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void displayYearToDate(){
        LocalDate toThisDate = LocalDate.now();
        LocalDate thisYear = LocalDate.now().withMonth(1).withDayOfMonth(1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formatToThisDate = toThisDate.format(dateFormatter);
        String formatBeginningThisYear = thisYear.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningThisYear + " to " + formatToThisDate);
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if((transaction.getRecordedDate().isAfter(thisYear) && (transaction.getRecordedDate().isBefore(toThisDate))  ) ){

                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void displayPreviousYear(){
        LocalDate beginningLastYear = LocalDate.now().minusYears(1).withMonth(1).withDayOfMonth(1);
        LocalDate endLastYear = LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(LocalDate.MAX.getDayOfMonth());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formatBeginningLastYear = beginningLastYear.format(dateFormatter);
        String formatEndLastYear = endLastYear.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningLastYear + " to " + formatEndLastYear);
        System.out.println();
        for (Transaction transaction : transactionList) {                                                           //for-each loop to iterate through
            if((transaction.getRecordedDate().isAfter(beginningLastYear) && (transaction.getRecordedDate().isBefore(endLastYear))  ) ){

                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void searchByVendor(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        System.out.print("Enter a Vendor:");
        String userInputVendor = scanner.nextLine().toLowerCase();

        System.out.println("\nSearching...");
        boolean isFound = false;

        for(Transaction transaction : transactionList){
            if(transaction.getVendor().toLowerCase().contains(userInputVendor)){
                System.out.println("Found a match!");
                isFound = true;

                String formatDate = transaction.getRecordedDate().format(dateFormatter);
                String formatTime = transaction.getRecordedTime().format(timeFormatter);
                System.out.printf("Date and Time: %s : %s |\t Description: %s |\t Vendor: %s |\t Amount: %.2f\n", formatDate, formatTime, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        if(!isFound){
            System.out.println("No Vendor has been found");
        }
        reports();
    }

    public void exit() {
        System.out.println("Exiting the application, please come again");
        System.exit(0);
    }

}