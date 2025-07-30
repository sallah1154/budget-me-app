package com.example.budgetme;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository repo;
    private LiveData<List<Transactions>> allTransactions;


    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repo = new TransactionRepository(application);
        allTransactions = repo.getAllTransactions();
    }

    public LiveData<List<Transactions>>getAllTransactions(){
        return allTransactions;
    }

    public LiveData<List<Transactions>>getTransactionsMonth(){
        return repo.getTransactionsMonth();
    }


    void insert(Transactions transaction){
        repo.insert(transaction);
    }
    void delete(Transactions transaction){
        repo.delete(transaction);
    }

}
