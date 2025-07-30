package com.example.budgetme;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity {

    private EditText etMonthlyBudget, etTotalSpent, etHousing, etTransporation, etFoodDining, etUtilites, etRemaining;
    private Button saveBudgetButton;
    TextView currentmbudget;
    private BudgetViewModel budgetVModel;
    private TransactionViewModel transactionVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Link XML IDs
        etMonthlyBudget = findViewById(R.id.etMonthlyBudget);
        etTotalSpent = findViewById(R.id.etTotalSpent);
        etHousing = findViewById(R.id.etHousing);
        etTransporation = findViewById(R.id.etTransporation);
        etFoodDining = findViewById(R.id.etFoodDining);
        etUtilites = findViewById(R.id.etUtilites);
        etRemaining = findViewById(R.id.etRemaining);
        saveBudgetButton = findViewById(R.id.save_budget_button);
        currentmbudget = findViewById(R.id.monthly_budget_view);

        // Add listeners to auto-update values
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateRemainingBudget();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etMonthlyBudget.addTextChangedListener(watcher);
        etHousing.addTextChangedListener(watcher);
        etTransporation.addTextChangedListener(watcher);
        etFoodDining.addTextChangedListener(watcher);
        etUtilites.addTextChangedListener(watcher);

        //stats button
        Button btnBudget = findViewById(R.id.btnStats);
        btnBudget.setOnClickListener(v -> {
            Intent intent = new Intent(BudgetActivity.this, StatsActivity.class);
            startActivity(intent);
        });

        budgetVModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        transactionVModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        budgetVModel.getRmonthlybudget().observe(this,currentbudget->{
            if(currentbudget !=null){
                currentmbudget.setText("current monthly budget $"+currentbudget);

                transactionVModel.getTransactionsMonth().observe(this, transactions -> {
                    double totalSpent = 0.0;
                    Map<String, Double> categoryTotals = new HashMap<>();

                    if (transactions != null) {
                        for (Transactions t : transactions) {
                            if ("Expense".equalsIgnoreCase(t.getType())) {
                                totalSpent += t.getAmount();
                                if(t.getCategory() != null){
                                    String categoryName = t.getCategory().getName();
                                    double current = categoryTotals.getOrDefault(categoryName,0.0);
                                    categoryTotals.put(categoryName, current + t.getAmount());
                                }
                            }
                        }
                    }
                    // for total spent and remaining
                    etTotalSpent.setText(String.format("$%.2f", totalSpent));
                    double remaining = currentbudget - totalSpent;
                    etRemaining.setText(String.format("$%.2f", remaining));

                    // Sort and show top 4 categories in logs
                    List<Map.Entry<String, Double>> sortedCategories = new ArrayList<>(categoryTotals.entrySet());
                    sortedCategories.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

                    int topLimit = Math.min(4, sortedCategories.size());
                    for (int i = 0; i < topLimit; i++) {
                        Map.Entry<String, Double> entry = sortedCategories.get(i);
                        android.util.Log.d("TopCategories", entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
                    }
                    if (topLimit > 0) etHousing.setText(String.format("$%.2f", sortedCategories.get(0).getValue()));
                    if (topLimit > 1) etTransporation.setText(String.format("$%.2f", sortedCategories.get(1).getValue()));
                    if (topLimit > 2) etFoodDining.setText(String.format("$%.2f", sortedCategories.get(2).getValue()));
                    if (topLimit > 3) etUtilites.setText(String.format("$%.2f", sortedCategories.get(3).getValue()));
                });

            } else {
                currentmbudget.setText("No budget set");
            }
        });

        saveBudgetButton.setOnClickListener(v->{

            String monthlyBudget = etMonthlyBudget.getText().toString();
            if(!monthlyBudget.isEmpty()){
                Double dMonthlyBudget = Double.parseDouble(monthlyBudget);
                Budget mbudget = new Budget(dMonthlyBudget);
                budgetVModel.insert(mbudget);

            }
        });
    }

    private void updateRemainingBudget() {
        double budget = parseDouble(etMonthlyBudget.getText().toString());
        double Housing = parseDouble(etHousing.getText().toString());
        double Transporation = parseDouble(etTransporation.getText().toString());
        double FoodDining = parseDouble(etFoodDining.getText().toString());
        double utilites = parseDouble(etUtilites.getText().toString());

        double totalSpent = Housing + Transporation + FoodDining + utilites;
        double remaining = budget - totalSpent;

        etTotalSpent.setText(String.format("$%.2f", totalSpent));
        etRemaining.setText(String.format("$%.2f", remaining));
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.replace("$", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
