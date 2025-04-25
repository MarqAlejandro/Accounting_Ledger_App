package com.plurasight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

        private LocalDate recordedDate;                                     //Transaction private variables
        private LocalTime recordedTime;
        private String description;
        private String vendor;
        private Double amount;

        public Transaction(){                                                   //empty constructor
            this.recordedDate = LocalDate.now();
            this.recordedTime = LocalTime.now();
            this.description = "default";
            this.vendor = "user";
            this.amount = 0.0;
        }

        //constructor with parameters
        public Transaction(LocalDate recordedDate, LocalTime recordedTime, String description, String vendor, Double amount) {
            this.recordedDate = recordedDate;
            this.recordedTime = recordedTime;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
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


