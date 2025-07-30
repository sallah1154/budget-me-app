package com.example.budgetme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transactions.class, Category.class,Budget.class},version =5,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class appDatabase extends RoomDatabase {
    public static volatile appDatabase INSTANCE;
    public abstract TransactionDao transactionDao();

    public abstract CategoryDAO categoryDao();

    public abstract BudgetDao budgetDao();
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


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
