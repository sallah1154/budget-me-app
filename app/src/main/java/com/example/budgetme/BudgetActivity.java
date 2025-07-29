package com.example.budgetme;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class BudgetActivity extends AppCompatActivity {

    private EditText etMonthlyBudget, etTotalSpent, etGroceries, etEntertainment, etRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        // Link XML IDs
        etMonthlyBudget = findViewById(R.id.etMonthlyBudget);
        etTotalSpent = findViewById(R.id.etTotalSpent);
        etGroceries = findViewById(R.id.etGroceries);
        etEntertainment = findViewById(R.id.etEntertainment);
        etRemaining = findViewById(R.id.etRemaining);

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
}
