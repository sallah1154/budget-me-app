package com.example.budgetme;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

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

        showtransaction = findViewById(R.id.show_transaction);
        vmodel = new ViewModelProvider(this).get(TransactionViewModel.class);
        Transactions testdata = new Transactions("expense","food",2.00);
        new Thread(()-> vmodel.insert(testdata)).start();

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



    }
}