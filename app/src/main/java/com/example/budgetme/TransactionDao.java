package com.example.budgetme;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface TransactionDao{
    @Query("SELECT * FROM Transactions")
    LiveData<List<Transactions>> getAllTransactions();

    @Query("SELECT * FROM Transactions WHERE date >= :pastdate")
    LiveData<List<Transactions>>getTransactionsMonth(Date pastdate);

    @Insert
    void insertTransaction(Transactions transaction );

    @Delete
    void deleteTransaction(Transactions transaction);



}
