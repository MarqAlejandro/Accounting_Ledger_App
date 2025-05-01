### Capstone Project
--------------------------------
##### Account Ledger Application

### Description
--------------------------------
The account ledger application is a 3-tiered user interface that implements a .csv file as a means of saving account information. 
It will prompt the user for input from a keyboard in order to traverse through each layer. 
Specific options will prompt the user for information required to either add an entry or to search for an entry

### Features
--------------------------------

list of features + Tiers of coding:
  1. ***Home Screen*** - main control method- leads to other methods depending on input
  2. -***Add Deposit & Make Payment*** - 2 methods that will add info to an ArrayList and save it to a .csv file
  3. -***Ledger Screen*** - 2nd control method - leads to 4 display methods that show different info, depends on input
  4.    --***Reports Screen*** - 3rd control method - leads to 5 options to display different info based on specified dates and time
  5.    --***Search By Vendor*** - method tha will prompt the user for a vendor's information and will print all entries that are relatively close to it
  6. ***Back, Home, & Exit*** - Navigation buttons that will bring us back a level higher or out of the application

### Screenshots of Output
---------------------------------

these 2 screenshots show the results of the 3-tier menus working intandem
![MenuTesting1](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img.png)
![MenuTesting1End](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_1.png)
---------------------------------

### this screenshot shows the results after getting all of the ledger display methods working
![DisplayLedgerTesting](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_2.png)

### this screenshot shows the results after getting 3 of 4 of the report display methods working
![DisplayReportsTesting](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_3.png)

### this screenshot shows the results after completing the 4th report display method and the searchByVendor method + a SortingByNewest method
![DisplayReportsTesting+SearchTesting](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_4.png)

### this screenshot shows the results after completing addDeposit and makePayment methods
![AddDeposit&MakePaymentTesting](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_5.png)

### this screenshot shows that the previous 2 methods (addDeposit and makePayment) were successfully added to the ArrayList and the .csv file
![ArrayList&.csvFileResults](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_6.png)

### My Favorite Block(s) of Code
---------------------------------

my favorite block of code is the sort method. Initially, when trying to implement it I found it difficult to try to use just LocalDate or just LocalTime alone,
so I concluded that it'd be easier to do if I were to have the LocalDateTime. However, I had to figure out a way to get the LocalDateTime from the .csv file information. 
The solution I came up with was to make LocalDateTime apart of the constructor, overload a setter in the Transaction class, so that it would take 2 arguements (1 LocalDate and 1 LocalTime), 
append them to a String with the exact format of a LocalDateTime object and parse it into a LocalDateTime object. 
Also, notice that the setter is being called at line 155 after all the other setters have done there job, 
thus making it viable for the LocalDate and LocalTime getters to pass as arguements for the LocalDateTime setter.
This was intentional by design.

![SortMethod](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_7.png)

Another favorite of mine was my implementation of recursion into all the menus, which drastically reduced the need for while loops and booleans in my code.
***In general*** At the end of each method, I made it so that its calling on others or calling itself if the input doesn't align with what was prompted.
The menus specifically links up with other menus, calls other functional methods, or (like I've stated previously) calls itself, effectively imitating a while loop.
The only means of escaping the code is by pressing the exit option in the home screen, which will execute the System.exit(0) protocol. 

![Menu](https://github.com/MarqAlejandro/LearnToCode_Capstones/blob/main/AccountingLedgerApp/img_8.png)
