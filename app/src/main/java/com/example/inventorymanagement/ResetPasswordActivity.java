package com.example.inventorymanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText editTextWorkerId, editTextNewPassword, editTextConfirmPassword;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userDao = AppDatabase.getDatabase(this).userDao();

        editTextWorkerId = findViewById(R.id.editTextWorkerId);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonReset = findViewById(R.id.buttonResetPassword);

        buttonReset.setOnClickListener(v -> {
            String workerId = editTextWorkerId.getText().toString().trim();
            String newPassword = editTextNewPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (workerId.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, R.string.error_passwords_dont_match, Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase.databaseWriteExecutor.execute(() -> {
                User user = userDao.getUser(workerId);
                if (user == null) {
                    runOnUiThread(() -> Toast.makeText(this, R.string.error_user_not_found, Toast.LENGTH_SHORT).show());
                } else {
                    user.setPassword(newPassword);
                    userDao.update(user);
                    runOnUiThread(() -> {
                        Toast.makeText(this, R.string.password_reset_success, Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        });
    }
}
