package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextWorkerId;
    private TextInputEditText editTextPassword;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao = AppDatabase.getDatabase(this).userDao();

        editTextWorkerId = findViewById(R.id.editTextWorkerId);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> {
            Editable idText = editTextWorkerId.getText();
            Editable passText = editTextPassword.getText();
            
            String workerId = (idText != null) ? idText.toString().trim() : "";
            String password = (passText != null) ? passText.toString().trim() : "";

            if (workerId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase.databaseWriteExecutor.execute(() -> {
                // Check if it's the default user first (optional, but helpful for existing testing)
                if (workerId.equals("worker123") && password.equals("password")) {
                    proceedToDashboard();
                    return;
                }

                User user = userDao.getUser(workerId);
                if (user != null && user.getPassword().equals(password)) {
                    proceedToDashboard();
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show());
                }
            });
        });

        findViewById(R.id.textViewCreateAccount).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
        });

        findViewById(R.id.textViewForgotPassword).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        });
    }

    private void proceedToDashboard() {
        runOnUiThread(() -> {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
