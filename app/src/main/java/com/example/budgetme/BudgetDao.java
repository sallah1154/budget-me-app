package com.example.budgetme;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BudgetDao {

    @Insert
    void insertMonthlyBudget(Budget budget);
    @Delete
    void deleteMonthlyBudget(Budget budget);

    @Update
    void insertTotalSpent(Budget budget);

    @Delete
    void RemainingBudget(Budget budget);

    @Query("SELECT monthlyBudget FROM Budget ORDER by uid DESC LIMIT 1")
    LiveData<Double> getMonthlyBudget();
/*
    @Query("SELECT totalSpent FROM Budget")
    LiveData<Double> getTotalSpent();

 */
/*
    @Query("SELECT remainingBudget FROM Budget")
    LiveData<Double> getRemainingBudget();

 */
}
