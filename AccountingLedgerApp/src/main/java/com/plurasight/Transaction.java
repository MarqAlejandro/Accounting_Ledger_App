package com.plurasight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Transaction {

        private LocalDate recordedDate;                                     //Transaction private variables
        private LocalTime recordedTime;
        private String description;
        private String vendor;
        private Double amount;
        private LocalDateTime recordedDateTime;

        public Transaction(){                                                   //empty constructor
            this.recordedDate = LocalDate.now();
            this.recordedTime = LocalTime.now();
            this.recordedDateTime = LocalDateTime.now();
            this.description = "default";
            this.vendor = "user";
            this.amount = 0.0;

        }

        //constructor with parameters made just in case there was a transaction that needs to be hardcoded into the .csv file
        public Transaction(LocalDate recordedDate, LocalTime recordedTime, LocalDateTime recordedDateTime, String description, String vendor, Double amount) {
            this.recordedDate = recordedDate;
            this.recordedTime = recordedTime;
            this.recordedDateTime = recordedDateTime;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

        public Transaction(String description, String user, double amount){                         //3rd constructor used in addDeposit and makePayment
            this.recordedDate = LocalDate.now();
            this.recordedTime = LocalTime.now();
            this.recordedDateTime = LocalDateTime.now();
            this.description = description;
            this.vendor = user;
            this.amount = amount;
        }

    public LocalDateTime getRecordedDateTime() {
        return recordedDateTime;
    }

    public void setRecordedDateTime(LocalDateTime recordedDateTime) {
        this.recordedDateTime = recordedDateTime;
    }

    public void setRecordedDateTime(LocalDate recordedDate, LocalTime recordedTime){
            String dateTimeString =  recordedDate.toString() + "T" + recordedTime;
            this.recordedDateTime = LocalDateTime.parse(dateTimeString);

    }

    public LocalDate getRecordedDate() {                                //list of getters and setters for each private variable
            return recordedDate;
        }

        public void setRecordedDate(LocalDate recordedDate) {
            this.recordedDate = recordedDate;
        }

        public LocalTime getRecordedTime() {
            return recordedTime;
        }

        public void setRecordedTime(LocalTime recordedTime) {
            this.recordedTime = recordedTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
}


