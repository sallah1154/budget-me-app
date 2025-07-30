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

public class BudgetActivity extends AppCompatActivity {

    private EditText etMonthlyBudget, etTotalSpent, etGroceries, etEntertainment, etRemaining;
    private Button saveBudgetButton;
    TextView currentmbudget;
    private BudgetViewModel budgetVModel;

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
        etGroceries = findViewById(R.id.etGroceries);
        etEntertainment = findViewById(R.id.etEntertainment);
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
        etGroceries.addTextChangedListener(watcher);
        etEntertainment.addTextChangedListener(watcher);

        //stats button
        Button btnBudget = findViewById(R.id.btnStats);
        btnBudget.setOnClickListener(v -> {
            Intent intent = new Intent(BudgetActivity.this, StatsActivity.class);
            startActivity(intent);
        });

        budgetVModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        budgetVModel.getRmonthlybudget().observe(this,currentbudget->{
            if(currentbudget !=null){
                currentmbudget.setText("current monthly budget $"+currentbudget);

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
        double groceries = parseDouble(etGroceries.getText().toString());
        double entertainment = parseDouble(etEntertainment.getText().toString());

        double totalSpent = groceries + entertainment;
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
