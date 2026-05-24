package com.example.inventorymanagement;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MonthlyReportActivity extends AppCompatActivity {

    private ProductViewModel viewModel;
    private TextView textViewMonthYear, textViewSales, textViewExpenses, textViewNet;
    private Calendar currentCalendar;
    private double currentSales = 0, currentExpenses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        textViewMonthYear = findViewById(R.id.textViewMonthYear);
        textViewSales = findViewById(R.id.textViewMonthlySales);
        textViewExpenses = findViewById(R.id.textViewMonthlyExpenses);
        textViewNet = findViewById(R.id.textViewNetBalance);
        ImageButton buttonPrev = findViewById(R.id.buttonPrevMonth);
        ImageButton buttonNext = findViewById(R.id.buttonNextMonth);

        currentCalendar = Calendar.getInstance();
        updateReport();

        buttonPrev.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateReport();
        });

        buttonNext.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateReport();
        });
    }

    private void updateReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        textViewMonthYear.setText(sdf.format(currentCalendar.getTime()));

        long startTime = getStartOfMonth(currentCalendar);
        long endTime = getEndOfMonth(currentCalendar);

        viewModel.getTotalSalesBetween(startTime, endTime).observe(this, sales -> {
            currentSales = sales != null ? sales : 0.0;
            updateUI();
        });

        viewModel.getTotalExpensesBetween(startTime, endTime).observe(this, expenses -> {
            currentExpenses = expenses != null ? expenses : 0.0;
            updateUI();
        });
    }

    private void updateUI() {
        textViewSales.setText(String.format(Locale.getDefault(), "KSh %.2f", currentSales));
        textViewExpenses.setText(String.format(Locale.getDefault(), "KSh %.2f", currentExpenses));
        double net = currentSales - currentExpenses;
        textViewNet.setText(String.format(Locale.getDefault(), "KSh %.2f", net));
        
        if (net >= 0) {
            textViewNet.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        } else {
            textViewNet.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }
    }

    private long getStartOfMonth(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    private long getEndOfMonth(Calendar cal) {
        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
