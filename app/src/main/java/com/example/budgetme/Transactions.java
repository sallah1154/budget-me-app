package com.example.budgetme;

import java.util.Date;
public class Transactions {
    private String type;       // "Income" or "Expense"
    private String description;
    private double amount;
    private Category category;
    private Date date;

    public Transactions(String type, String description, double amount) {
        this.type = type;
        this.description = description;
        this.amount = amount;

    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public java.util.Date getDate() {return date;}
}
