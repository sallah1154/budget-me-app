package com.example.budgetme;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity {

    private static final String TAG = "EmailPasswordActivity";
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
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (!validateInputs(email, password)) {
                return;
            }
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "No internet connection. Please check your network.", Toast.LENGTH_LONG).show();
                return;
            }
            signIn(email, password);
        });

        buttonCreateAccount.setOnClickListener(view -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (!validateInputs(email, password)) {
                return;
            }
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "No internet connection. Please check your network.", Toast.LENGTH_LONG).show();
                return;
            }
            createAccount(email, password);
        });
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(EmailPasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(EmailPasswordActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EmailPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            String errorMessage = "Authentication failed.";
                            if (task.getException() != null) {
                                if (task.getException() instanceof FirebaseAuthException) {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    switch (errorCode) {
                                        case "ERROR_INVALID_EMAIL":
                                            errorMessage = "Invalid email format.";
                                            break;
                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            errorMessage = "Email is already registered.";
                                            break;
                                        case "ERROR_WEAK_PASSWORD":
                                            errorMessage = "Password must be at least 6 characters.";
                                            break;
                                        case "ERROR_OPERATION_NOT_ALLOWED":
                                            errorMessage = "Email/Password authentication is not enabled.";
                                            break;
                                        default:
                                            errorMessage = task.getException().getMessage();
                                    }
                                } else {
                                    errorMessage = task.getException().getMessage();
                                    if (errorMessage.contains("network")) {
                                        errorMessage = "Network error. Please check your internet connection and try again.";
                                    }
                                }
                            }
                            Toast.makeText(EmailPasswordActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Intent intent = new Intent(EmailPasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            String errorMessage = "Authentication failed.";
                            if (task.getException() != null) {
                                if (task.getException() instanceof FirebaseAuthException) {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    switch (errorCode) {
                                        case "ERROR_INVALID_EMAIL":
                                            errorMessage = "Invalid email format.";
                                            break;
                                        case "ERROR_WRONG_PASSWORD":
                                            errorMessage = "Incorrect password.";
                                            break;
                                        case "ERROR_USER_NOT_FOUND":
                                            errorMessage = "No account exists for this email.";
                                            break;
                                        default:
                                            errorMessage = task.getException().getMessage();
                                    }
                                } else {
                                    errorMessage = task.getException().getMessage();
                                    if (errorMessage.contains("network")) {
                                        errorMessage = "Network error. Please check your internet connection and try again.";
                                    }
                                }
                            }
                            Toast.makeText(EmailPasswordActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}