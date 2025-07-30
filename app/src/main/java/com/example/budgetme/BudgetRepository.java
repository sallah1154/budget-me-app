package com.example.budgetme;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BudgetRepository {
    private BudgetDao budgetDao;
    private LiveData<Double> monthlyBudget;

    public BudgetRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        budgetDao = db.budgetDao();
        monthlyBudget = db.budgetDao().getMonthlyBudget();
    }


    LiveData<Double> getMonthlyBudget(){
        return monthlyBudget;
    }

    void insert(Budget budget){
        appDatabase.databaseWriteExecutor.execute(() ->{
            budgetDao.insertMonthlyBudget(budget);
        });
    }

    void delete(Budget budget){
        appDatabase.databaseWriteExecutor.execute(()->{
            budgetDao.deleteMonthlyBudget(budget);
        });
    }




}
