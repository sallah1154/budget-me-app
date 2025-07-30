package com.example.budgetme;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private TransactionDao transactionDao;
    private LiveData<List<Transactions>>allTransactions;


    public TransactionRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = db.transactionDao().getAllTransactions();

    }


    LiveData<List<Transactions>> getAllTransactions(){
        return allTransactions;
    }

    void insert(Transactions transaction){
        appDatabase.databaseWriteExecutor.execute(() ->{
            transactionDao.insertTransaction(transaction);
        });
    }

    void delete(Transactions transaction){
        appDatabase.databaseWriteExecutor.execute(()->{
            transactionDao.deleteTransaction(transaction);
        });
    }

}
