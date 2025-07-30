package com.example.budgetme;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {


    private BudgetRepository budgetrepo;
    private LiveData<Double> rmonthlybudget;
    public BudgetViewModel(@NonNull Application application) {
        super(application);
        budgetrepo = new BudgetRepository(application);
        rmonthlybudget = budgetrepo.getMonthlyBudget();
    }

    public LiveData<Double>getRmonthlybudget(){ return rmonthlybudget;}

    void  insert(Budget budget){
        budgetrepo.insert(budget);
    }
    void delete(Budget budget){budgetrepo.delete(budget);}
}
