package com.example.inventorymanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CreateAccountActivity extends AppCompatActivity {

    private TextInputEditText editTextWorkerId, editTextPassword, editTextConfirmPassword;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userDao = AppDatabase.getDatabase(this).userDao();

        editTextWorkerId = findViewById(R.id.editTextWorkerId);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonCreate = findViewById(R.id.buttonCreateAccount);

        buttonCreate.setOnClickListener(v -> {
            String workerId = editTextWorkerId.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (workerId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, R.string.error_passwords_dont_match, Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase.databaseWriteExecutor.execute(() -> {
                User existingUser = userDao.getUser(workerId);
                if (existingUser != null) {
                    runOnUiThread(() -> Toast.makeText(this, R.string.error_user_exists, Toast.LENGTH_SHORT).show());
                } else {
                    userDao.insert(new User(workerId, password));
                    runOnUiThread(() -> {
                        Toast.makeText(this, R.string.account_created, Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        });
    }
}
