package com.example.inventorymanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class ExpensesActivity extends AppCompatActivity {

    private ProductViewModel viewModel;
    private TextInputEditText editTextDescription, editTextAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        editTextDescription = findViewById(R.id.editTextExpenseDescription);
        editTextAmount = findViewById(R.id.editTextExpenseAmount);
        Button buttonAdd = findViewById(R.id.buttonAddExpense);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewExpenses);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getAllExpenses().observe(this, adapter::submitList);

        buttonAdd.setOnClickListener(v -> {
            String desc = editTextDescription.getText().toString().trim();
            String amountStr = editTextAmount.getText().toString().trim();

            if (desc.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                Expense expense = new Expense(desc, amount, System.currentTimeMillis());
                viewModel.insertExpense(expense);
                
                editTextDescription.setText("");
                editTextAmount.setText("");
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
