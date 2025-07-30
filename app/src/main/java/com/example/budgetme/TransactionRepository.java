package com.example.budgetme;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Calendar;
import java.util.Date;
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
    public LiveData<List<Transactions>>getTransactionsMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date pastDate = calendar.getTime();
        return transactionDao.getTransactionsMonth(pastDate);
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
