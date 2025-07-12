package com.example.budgetme;

public class Transactions {
    private String type;// "Income" or "Expense"
    long income;
    long expenses;
    private String description;
    private double amount;

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
}
