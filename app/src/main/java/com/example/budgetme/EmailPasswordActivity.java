package com.example.budgetme;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.email_password), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        EditText editEmail = findViewById(R.id.input_email);
        EditText editPassword = findViewById(R.id.input_password);
        Button buttonLogin = findViewById(R.id.Login_Button);
        Button buttonCreateAccount = findViewById(R.id.Create_account);

        buttonLogin.setOnClickListener(view -> {
            String Email = editEmail.getText().toString().trim();
            String Password = editPassword.getText().toString().trim();


            if(Email.isEmpty() || Password.isEmpty() ){
                Toast.makeText(this,"password or email not entered",Toast.LENGTH_LONG).show();
            } else{
                signIn(Email, Password);

            }




        });
        buttonCreateAccount.setOnClickListener(view -> {
            String Email = editEmail.getText().toString().trim();
            String Password = editPassword.getText().toString().trim();

            if(Email.isEmpty() || Password.isEmpty() ){
                Toast.makeText(this,"password or email not entered",Toast.LENGTH_LONG).show();
            } else{
                createAccount(Email, Password);

            }

        });













    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(EmailPasswordActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG,"createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(EmailPasswordActivity.this,"Authentication success.", Toast.LENGTH_LONG).show();

                        } else {
                            Log.w(TAG,"createUserWithEmail:failure",task.getException());
                            Toast.makeText(EmailPasswordActivity.this,"Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null){
                                Intent intent = new Intent(EmailPasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        } else {
                            Log.w(TAG, "signInwithEmail:failure",task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


}
