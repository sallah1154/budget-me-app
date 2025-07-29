package com.example.budgetme;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;



public class AddTransactionActivity extends AppCompatActivity {
    private Category selectedCategory;
    private TransactionViewModel tViewModel;

    private void loadCategoryRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Categorymanager categoryManager = Categorymanager.getInstance();

        CategoryAdapter adapter = new CategoryAdapter(
                this,
                categoryManager.getCategories(),
                new OnCategoryClickListener() {
                    @Override
                    public void onCategoryClick(Category category) {
                        selectedCategory = category;
                        Log.d("AddTransactionActivity", "Selected category: " + category.getName());
                    }
                }
        );
        recyclerView.setAdapter(adapter);
    }

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


        AutoCompleteTextView typeEditText = findViewById(R.id.input_transaction_type);
        String[] transactionTypes = {"Income", "Expense"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, transactionTypes);
        typeEditText.setAdapter(typeAdapter);

        EditText AmountEditText = findViewById(R.id.input_transaction_amount);
        EditText TransactionNameEditText = findViewById(R.id.input_transaction_name);
        Button addTransactionButton = findViewById(R.id.btn_add_transaction);

        tViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        loadCategoryRecycler();

        addTransactionButton.setOnClickListener(v->{
            String type = typeEditText.getText().toString();
            String amount = AmountEditText.getText().toString();
            String transactionname = TransactionNameEditText.getText().toString();

            if(type.isEmpty() || amount.isEmpty() || transactionname.isEmpty()){
                Toast.makeText(this,"all fields are not filled out",Toast.LENGTH_LONG).show();
            }

            double damount;
            try {
                damount = Double.parseDouble(amount);
            } catch(NumberFormatException e){
                Toast.makeText(this,"invalid amount",Toast.LENGTH_LONG).show();
                return;
            }

            if (selectedCategory == null) {
                Toast.makeText(this, "Please select a category.", Toast.LENGTH_LONG).show();
                return;
            }

            Transactions transaction = new Transactions(type,transactionname,damount,selectedCategory);
            tViewModel.insert(transaction);

            Toast.makeText(this,"Transaction added",Toast.LENGTH_LONG).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
