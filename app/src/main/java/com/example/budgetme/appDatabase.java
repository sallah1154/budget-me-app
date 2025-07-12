package com.example.budgetme;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Transactions.class},version =1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class appDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
