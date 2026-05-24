package com.example.inventorymanagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class DailySalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sales);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SaleAdapter adapter = new SaleAdapter();
        recyclerView.setAdapter(adapter);

        ProductViewModel viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        
        // Fetch Today's sales
        viewModel.getSalesSince(getStartOfToday()).observe(this, adapter::submitList);
    }

    private long getStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
