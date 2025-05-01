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

    private Scanner scanner = new Scanner(System.in);                                   // global scanner and arraylist initialized
    private List<Transaction> transactionList = new ArrayList<>();


    public void homeScreen() {                                                          //1st tier screen - leads to other methods including another screen
        transactionList.clear();                                                        //clears the arraylist of old data, only relevant when going in and out of homescreen method
        loadTransactionInfo();                                                          //load the arraylist with latest .csv file
        sortNewest();                                                                   //sort the data by its LocalDateTime variable

        System.out.print("""
                
                Welcome, please input one of the following: \
                
                \tD) Add Deposit\
                
                \tP) Make Payment (Debit)\
                
                \tL) Ledger\
                
                \tX) Exit\
                
                Enter:\s""");

        int userInputHome = 0;                                                          //prompt the user for input
        String userInput = scanner.nextLine();                                          //filter through the input
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

        switch (userInputHome) {                                                    //assumes user input is valid and plays a method depending on what the user's input is
            case 1:
                addDeposit();
            case 2:
                makePayment();
            case 3:
                ledger();
            case 4:
                exit();
            default:
                System.out.println("Please try again");
                homeScreen();                                                       //if user's input is invalid it will recurse back to the top of the method asking the user to input again
        }
    }//end of homeScreen method

    public void addDeposit() {                                                      //prompt user for transaction information and add it to the .csv  file
        try {
        System.out.print("Enter Transaction Description: ");
        String transactionDescription = scanner.nextLine();
        System.out.print("Enter Transaction Vendor: ");
        String transactionVendor = scanner.nextLine();
        System.out.print("Enter Amount Added to the Account: ");
        double transactionAmount = scanner.nextDouble();
        scanner.nextLine();

        Transaction transaction = new Transaction(transactionDescription, transactionVendor, transactionAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");                                  //required because time would include fractions of seconds
        LocalTime formatTime = LocalTime.parse(transaction.getRecordedTime().format(formatter));


        FileWriter writer = new FileWriter("transactions.csv",true);                            //append has be set to on, so it will add to the bottom of the file

        writer.write(transaction.getRecordedDate() + "|" + formatTime + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount());
        writer.write("\n");


        writer.close();

        homeScreen();                                                                                               //return to homeScreen

        } catch (InputMismatchException eInput) {
            System.out.println("Amount input was non-numeric, please try again");
            scanner.nextLine();
            addDeposit();
        } catch (IOException eIO) {
            System.out.println("error with .csv file naming, please check if its the correct save file");
        }
    }

    public void makePayment() {                                                 //similar to the addDeposit method, exception is the getAmount is switched to negative value
        try {
            System.out.print("Enter Transaction Description: ");
            String transactionDescription = scanner.nextLine();
            System.out.print("Enter Transaction Recipient: ");
            String transactionVendor = scanner.nextLine();
            System.out.print("Enter Amount Paying out of the Account: -");
            double transactionAmount = scanner.nextDouble();
            scanner.nextLine();

            Transaction transaction = new Transaction(transactionDescription, transactionVendor, transactionAmount);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            LocalTime formatTime = LocalTime.parse(transaction.getRecordedTime().format(formatter));


            FileWriter writer = new FileWriter("transactions.csv",true);

                                                                                                                                                            //only difference for this method and addDeposit
            writer.write(transaction.getRecordedDate() + "|" + formatTime + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + -transaction.getAmount());
            writer.write("\n");
            writer.close();

            homeScreen();                                                                                            //return to homeScreen

        } catch (InputMismatchException eInput) {
            System.out.println("Amount input was non-numeric, please try again");
            scanner.nextLine();
            makePayment();
        } catch (IOException eIO) {
            System.out.println("error with .csv file naming, please check if its the correct save file");
        }
    }

    public void loadTransactionInfo() {                                                                          //method to load Transaction info from .csv file onto ArrayList

        try {
            //System.out.println("Loading Account Information");

            BufferedReader bufReader = new BufferedReader(new FileReader("transactions.csv"));     //BufferedReader variable that takes a FileReader as arguement that takes a .csv file arguement
            String FileInput;                                                                               //String Variable to hold transaction info

            bufReader.readLine();                                                                           //skip the first line, assumes that the first line is headers and garbage data

            while ((FileInput = bufReader.readLine()) != null) {                                            //in the midst of while loop read a line from .csv file and load it onto String Variable and check if it comes out null
                String[] tokens = FileInput.split(Pattern.quote("|"));                                   //load the line onto a String array so that it can be partitioned by the pattern "|"
                Transaction transaction = new Transaction();                                                //create an empty Transaction object
                if (tokens.length == 5) {
                    transaction.setRecordedDate(LocalDate.parse(tokens[0]));
                    transaction.setRecordedTime(LocalTime.parse(tokens[1]));
                    transaction.setDescription(tokens[2]);
                    transaction.setVendor(tokens[3]);
                    transaction.setAmount(Double.parseDouble(tokens[4]));                                   //will load and set all transaction information only if there is exactly 5 elements in the String Array
                    transaction.setRecordedDateTime(transaction.getRecordedDate(), transaction.getRecordedTime());              //this is required for sorting purposes, overloaded a method
                } else {
                    System.out.println("error: missing or too much information on a given transaction");
                }
                transactionList.add(transaction);                                                         //load the object onto the ArrayList for Transaction objects
            }
            bufReader.close();

        } catch (IOException e) {                                                                       //in case of an error with I/O, however should never proc
            System.out.println("error with .csv file naming, please check if its the correct save file");

        }
    }

    public void sortNewest(){                                                                           //method to sort all objects in the arraylist, will show the most recent transaction on top
        transactionList.sort((transaction1, transaction2) -> transaction2.getRecordedDateTime().compareTo(transaction1.getRecordedDateTime()));
    }

    public void ledger() {                                                                              //similar logic to the homescreen
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
                displayAll();                                                                   //
            case 2:                                                                             //
                displayDepositOnly();                                                           //  display methods
            case 3:                                                                             //
                displayPaymentsOnly();                                                          //
            case 4:
                reports();                                                                      //leads to 3rd Tier Screen
            case 5:
                System.out.println("Returning to Home Page...");                                //as the 2nd Tier screen allows you to go back to the 1st Tier screen
                homeScreen();
            default:
                System.out.println("Please try again");                                         //if user's input is invalid it will recurse back to the top of the method asking the user to input again
                ledger();
        }
    }

    public void displayAll() {                                                                  //display all entries
        System.out.println("Printing All Account Transaction Information:");
        System.out.println();
        for (Transaction transaction : transactionList) {

            transaction.displayInformation();
        }
        System.out.println("\nReturning to Ledger Display Screen...");
        ledger();
    }

    public void displayDepositOnly(){                                                       //display only deposits aka positive amount values

        System.out.println("Printing All Deposits Information:");
        System.out.println();
        for (Transaction transaction : transactionList) {
            if(transaction.getAmount() > 0) {

                transaction.displayInformation();
            }
        }
        System.out.println("\nReturning to Ledger Display Screen...");
        ledger();
    }

    public void displayPaymentsOnly(){                                                                  //display only payments aka negative amount values

        System.out.println("Printing All Payments Information:");
        System.out.println();
        for (Transaction transaction : transactionList) {
            if(transaction.getAmount() < 0) {

                transaction.displayInformation();
            }
        }
        System.out.println("Returning to Ledger Display Screen...");
        ledger();
    }

    public void reports() {                                                                      //similar logic to the homescreen, only difference is input validation is done through try-catch system
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
                    ledger();                                                               //lead back to 2nd Tier screen
                case 1:
                    displayMonthToDate();                                                   //
                case 2:                                                                     //
                    displayPreviousMonth();                                                 //
                case 3:                                                                     // display methods for specific time periods
                    displayYearToDate();                                                    //
                case 4:                                                                     //
                    displayPreviousYear();                                                  //
                case 5:
                    searchByVendor();                                                       //jump to searchByVendor method
                default:
                    System.out.println("Input was not one of the options. Please try again"); //if input is not between 0-5 then it will recurse back to the top of the method
                    reports();
            }
        } catch (InputMismatchException e) {
            System.out.println("A non-number was inputted please try again");               //if a non-numeric value is inputted then it will eat the last line
            String eater = scanner.nextLine();
            reports();                                                                      //and recurse back to the top of the method
        }
    }

    public void displayMonthToDate(){                                                       //display all transactions within the month of LocalDate.now()
        LocalDate toThisDate = LocalDate.now();
        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatToThisDate = toThisDate.format(dateFormatter);
        String formatBeginningThisMonth = thisMonth.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningThisMonth + " to " + formatToThisDate);
        System.out.println();
        for (Transaction transaction : transactionList) {
            if((transaction.getRecordedDate().isAfter(thisMonth) && (transaction.getRecordedDate().isBefore(toThisDate))  ) ){      //not inclusive, if thisMonth and toThisDate are the same date

                transaction.displayInformation();

            }
            if (transaction.getRecordedDate().isEqual(toThisDate)){                                                                 //in cases where the thisMonth and toThisDate are the same

                transaction.displayInformation();

            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void displayPreviousMonth(){                                                   //display all transactions from the previous month
        LocalDate beginningLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.MAX.minusMonths(1).getDayOfMonth());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatBeginningLastMonth = beginningLastMonth.format(dateFormatter);
        String formatEndLastMonth = endLastMonth.format(dateFormatter);
        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningLastMonth + " to " + formatEndLastMonth);
        System.out.println();
        for (Transaction transaction : transactionList) {
            if((transaction.getRecordedDate().isAfter(beginningLastMonth) && (transaction.getRecordedDate().isBefore(endLastMonth))  ) ){

                transaction.displayInformation();
            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void displayYearToDate(){                                                        //similar to the monthToDate method but tailored to go back to the beginning of this year
        LocalDate toThisDate = LocalDate.now();
        LocalDate thisYear = LocalDate.now().withMonth(1).withDayOfMonth(1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatToThisDate = toThisDate.format(dateFormatter);
        String formatBeginningThisYear = thisYear.format(dateFormatter);
        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningThisYear + " to " + formatToThisDate);
        System.out.println();
        for (Transaction transaction : transactionList) {
            if((transaction.getRecordedDate().isAfter(thisYear) && (transaction.getRecordedDate().isBefore(toThisDate))  ) ){

                transaction.displayInformation();

            }
            if (transaction.getRecordedDate().isEqual(toThisDate)){                                                                 //in cases where the thisYear and toThisDate are the same

                transaction.displayInformation();

            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void displayPreviousYear(){                                                      //similar to previousMonth method but shows tailored to the previous year
        LocalDate beginningLastYear = LocalDate.now().minusYears(1).withMonth(1).withDayOfMonth(1);
        LocalDate endLastYear = LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(LocalDate.MAX.getDayOfMonth());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formatBeginningLastYear = beginningLastYear.format(dateFormatter);
        String formatEndLastYear = endLastYear.format(dateFormatter);

        System.out.println("Printing All Payments Information...");
        System.out.println("From " + formatBeginningLastYear + " to " + formatEndLastYear);
        System.out.println();
        for (Transaction transaction : transactionList) {
            if((transaction.getRecordedDate().isAfter(beginningLastYear) && (transaction.getRecordedDate().isBefore(endLastYear))  ) ){

                transaction.displayInformation();
            }
        }
        System.out.println("Returning to Reports Display Screen...");
        reports();
    }

    public void searchByVendor(){                                                   //prompt user for vendor name and print all entries that contain the user's input, and yes if 1 letter is inputted it will print all entries that contain that letter
        System.out.print("Enter a Vendor:");
        String userInputVendor = scanner.nextLine().toLowerCase();

        System.out.println("\nSearching...");
        boolean isFound = false;

        for(Transaction transaction : transactionList){
            if(transaction.getVendor().toLowerCase().contains(userInputVendor)){
                System.out.println("Found a match!");
                isFound = true;

                transaction.displayInformation();
            }
        }
        if(!isFound){
            System.out.println("No Vendor has been found");
        }
        reports();
    }

    public void exit() {                                                        //exit method created just for readability
        System.out.println("Exiting the application, please come again");
        System.exit(0);
    }

}