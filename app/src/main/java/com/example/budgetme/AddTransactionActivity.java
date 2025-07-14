package com.example.budgetme;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity  {


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

        if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
         TransactionViewModel tViewModel;


        EditText TypeEditText = findViewById(R.id.input_transaction_type);

        EditText AmountEditText = findViewById(R.id.input_transaction_amount);
        EditText TransactionNameEditText = findViewById(R.id.input_transaction_name);
        Button addTransactionButton = findViewById(R.id.btn_add_transaction);


        tViewModel  = new ViewModelProvider(this).get(TransactionViewModel.class);






        addTransactionButton.setOnClickListener(v->{
            String type = TypeEditText.getText().toString();
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
            Transactions transaction = new Transactions(type,transactionname,damount);


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