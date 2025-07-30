package com.example.budgetme;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Ignore;

import java.util.Date;
import java.util.List;

@Entity
public class Transactions {
    @PrimaryKey(autoGenerate = true)
    public int uid;


    @ColumnInfo(name = "Type")
    private String type;       // "Income" or "Expense"

    /*
    @ColumnInfo(name = "description")
    private String description;

     */
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "Category")
    private Category category;
    @ColumnInfo(name = "Date")
    private Date date;
    @ColumnInfo(name = "name")
    private String name;


    public Transactions(String type, String name, double amount) {
        this.type = type;
        //this.description = description;
        this.amount = amount;
        this.name = name;

    }
@Ignore
    public Transactions(String type, String name, double amount,Category category, Date date){
        this.type = type;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.date = date;
    }

    public String getType() {
        return type;
    }
/*
    public String getDescription() {
        return description;
    }

 */

    public double getAmount() {
        return amount;
    }

    public Category getCategory(){
        return category;
    }

    public java.util.Date getDate() {return date;}

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
