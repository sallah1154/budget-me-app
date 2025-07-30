package com.example.budgetme;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Budget {
    @PrimaryKey(autoGenerate = true)
    public int uid;



    @ColumnInfo(name = "monthlyBudget")
    Double monthlyBudget;
    /*
    @ColumnInfo(name =  "totalSpent")
    Double totalSpent;

     */
    /*
    @ColumnInfo(name = "remainingBudget")
    Double remainingBudget;

     */

    public Budget(Double monthlyBudget){
        this.monthlyBudget = monthlyBudget;
        //this.totalSpent = totalSpent;
       // this.remainingBudget= remainingBudget;
    }

    public Double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(Double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }
/*
    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

 */
/*
    public Double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(Double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

 */
}
