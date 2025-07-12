package com.example.budgetme;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao{
    @Query("SELECT * FROM transactions")
    List<Transactions> getAllTransactions();

    @Insert
    void insertTransaction(Transactions transaction );

    @Delete
    void deleteTransaction(Transactions transaction);
}
