package com.example.budgetme;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView showtransaction;
    private TransactionViewModel vmodel;

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

        FrameLayout addTransactionBtn = findViewById(R.id.btn_grocery);
        addTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open AddTransactionActivity when the icon is clicked
                Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });

        Button LogOutButton = findViewById(R.id.Log_out_Button);
        LogOutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this,EmailPasswordActivity.class);
            startActivity(intent);
        });
        Button btnBudget = findViewById(R.id.btnBudget);
        btnBudget.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
            startActivity(intent);
        });


        showtransaction = findViewById(R.id.show_transaction);
        vmodel = new ViewModelProvider(this).get(TransactionViewModel.class);



        vmodel.getAllTransactions().observe(this,transactions ->{
            if(transactions == null || transactions.isEmpty()){
                return;
            }

            StringBuilder sb = new StringBuilder();
            for(Transactions t : transactions){
                sb.append("type: ").append(t.getType()).append("\n")
                        .append("name: ").append(t.getName()).append("\n")
                        .append("amount: ").append(t.getAmount()).append("\n");

            }
            showtransaction.setText(sb.toString());
        });


        btnBudget.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
            startActivity(intent);
        });



    }
}
