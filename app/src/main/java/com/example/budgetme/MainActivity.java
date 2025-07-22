package com.example.budgetme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TransactionViewModel vmodel;
    private transactionAdaptor adapter;

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

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.transaction_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new transactionAdaptor();
        recyclerView.setAdapter(adapter);

        // Floating Action Button for adding transactions
        FloatingActionButton addTransactionBtn = findViewById(R.id.btn_add_transaction);
        addTransactionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        // Logout button
        com.google.android.material.button.MaterialButton logOutButton = findViewById(R.id.Log_out_Button);
        logOutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, EmailPasswordActivity.class);
            startActivity(intent);
            finish();
        });

        // Observe transactions
        vmodel = new ViewModelProvider(this).get(TransactionViewModel.class);
        vmodel.getAllTransactions().observe(this, transactions -> {
            if (transactions == null || transactions.isEmpty()) {
                adapter.setTransactions(new ArrayList<>());
                return;
            }
            adapter.setTransactions(transactions);
        });
    }
}