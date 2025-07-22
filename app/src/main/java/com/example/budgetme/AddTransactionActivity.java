package com.example.budgetme;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    private TransactionViewModel tViewModel;
    private Categorymanager categoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_transaction_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        categoryManager = new Categorymanager();

        // Initialize transaction type dropdown
        AutoCompleteTextView typeEditText = findViewById(R.id.input_transaction_type);
        String[] transactionTypes = {"Income", "Expense"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, transactionTypes);
        typeEditText.setAdapter(typeAdapter);

        // Initialize category dropdown
        AutoCompleteTextView categoryEditText = findViewById(R.id.input_category);
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categoryManager.getCategories()) {
            categoryNames.add(category.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categoryNames);
        categoryEditText.setAdapter(categoryAdapter);

        // Input fields
        TextInputEditText nameEditText = findViewById(R.id.input_transaction_name);
        TextInputEditText amountEditText = findViewById(R.id.input_transaction_amount);
        com.google.android.material.button.MaterialButton addTransactionButton = findViewById(R.id.btn_add_transaction);

        addTransactionButton.setOnClickListener(v -> {
            String type = typeEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String amount = amountEditText.getText().toString().trim();
            String categoryName = categoryEditText.getText().toString().trim();

            if (type.isEmpty() || name.isEmpty() || amount.isEmpty() || categoryName.isEmpty()) {
                Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_LONG).show();
                return;
            }

            double dAmount;
            try {
                dAmount = Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_LONG).show();
                return;
            }

            // Find selected category
            Category selectedCategory = null;
            for (Category category : categoryManager.getCategories()) {
                if (category.getName().equals(categoryName)) {
                    selectedCategory = category;
                    break;
                }
            }

            Transactions transaction = new Transactions(type, name, dAmount);
            transaction.setCategory(selectedCategory);
            transaction.setDate(new Date());

            tViewModel.insert(transaction);
            Toast.makeText(this, "Transaction added", Toast.LENGTH_LONG).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}