package com.example.budgetme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Transactions.class},version =2,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class appDatabase extends RoomDatabase {
    public static volatile appDatabase INSTANCE;
    public abstract TransactionDao transactionDao();


    public static appDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (appDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), appDatabase.class,"budget_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
