package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set Current Date
        TextView textViewDate = findViewById(R.id.textViewDate);
        String currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        textViewDate.setText(currentDate);

        TextView textViewWorkerStatus = findViewById(R.id.textViewWorkerStatus);
        textViewWorkerStatus.setText(getString(R.string.worker_status, "Worker 123"));

        TextView textViewTodaySales = findViewById(R.id.textViewTodaySales);

        ProductViewModel viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Observe Today's Sales
        viewModel.getTotalSalesSince(getStartOfToday()).observe(this, total ->
                textViewTodaySales.setText(String.format(Locale.getDefault(), "KSh %.2f", total != null ? total : 0.0)));

        // Set Click Listeners for "Apps"
        findViewById(R.id.cardInventory).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, MainActivity.class)));

        findViewById(R.id.cardAddProduct).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AddEditProductActivity.class)));

        findViewById(R.id.cardSales).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, RecordSaleActivity.class)));

        findViewById(R.id.cardTodaySalesHistory).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, DailySalesActivity.class)));

        findViewById(R.id.cardLowStock).setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            Toast.makeText(this, "Showing all items. Filter for low stock coming soon!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.cardWorkers).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, MonthlyReportActivity.class)));

        findViewById(R.id.cardSettings).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class)));

        findViewById(R.id.cardDocuments).setOnClickListener(v ->
                Toast.makeText(this, R.string.documents, Toast.LENGTH_SHORT).show());

        findViewById(R.id.cardLogout).setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finishAffinity();
        });

        findViewById(R.id.cardExpenses).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, ExpensesActivity.class)));
    }

    private void showQuickStats() {
        ProductViewModel viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        viewModel.getProductCount().observe(this, count ->
                viewModel.getLowStockCount().observe(this, lowStock -> {
                    String stats = String.format(Locale.getDefault(),
                            "Total Products: %d\nLow Stock Items: %d", count, lowStock);
                    Toast.makeText(this, stats, Toast.LENGTH_LONG).show();
                }));
    }

    private long getStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
