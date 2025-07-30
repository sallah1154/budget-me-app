package com.example.budgetme;

import android.os.Bundle;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TransactionViewModel vmodel;
    private transactionAdaptor adapter;

    private BudgetViewModel budgetVModel;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView totalBudgetText = findViewById(R.id.summary_total_budget);
        TextView spentText = findViewById(R.id.summary_spent);
        TextView remainingText = findViewById(R.id.summary_remaining);


        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.transaction_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new transactionAdaptor();
        recyclerView.setAdapter(adapter);

        // adding transactions
        FrameLayout addTransactionBtn = findViewById(R.id.btn_add_transaction);
        addTransactionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        //add category button
        FrameLayout addCategoryBtn = findViewById(R.id.btn_category);
        addCategoryBtn.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, AddCategoryActivity.class);
            startActivity(intent2);
        });

        Button btnBudget = findViewById(R.id.btnBudget);
        btnBudget.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
            startActivity(intent);
        });

        // Logout button
        com.google.android.material.button.MaterialButton logOutButton = findViewById(R.id.Log_out_Button);
        logOutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent3 = new Intent(MainActivity.this, EmailPasswordActivity.class);
            startActivity(intent3);
            finish();
        });

        vmodel = new ViewModelProvider(this).get(TransactionViewModel.class);





        vmodel.getAllTransactions().observe(this,transactions ->{
            if(transactions == null || transactions.isEmpty()){
                adapter.setTransactions(new ArrayList<>());
                return;
            }
            adapter.setTransactions(transactions);

        });

        budgetVModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        budgetVModel.getRmonthlybudget().observe(this,currentbudget->{
            if(currentbudget!=null){
                totalBudgetText.setText("Total Budget: $"+currentbudget);
                vmodel.getTransactionsMonth().observe(this,transactions->{
                    double totalSpent =0.0;

                    for(Transactions t :transactions){
                        if("Expense".equalsIgnoreCase(t.getType())){
                            totalSpent +=t.getAmount();
                        }

                    }
                    double remaining = currentbudget - totalSpent;

                    spentText.setText((String.format("Total Spent: $%.2f",totalSpent)));
                    remainingText.setText((String.format("Remaining budget $%.2f",remaining)));
                });
            } else {
                totalBudgetText.setText("Total Budget: ");
            }


        });




    }
}
