package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;

    private final ActivityResultLauncher<Intent> addProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Product is now saved directly in AddEditProductActivity
            }
    );

    private final ActivityResultLauncher<Intent> editProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Product is now updated directly in AddEditProductActivity
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton buttonAddProduct = findViewById(R.id.fabAddProduct);
        buttonAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditProductActivity.class);
            addProductLauncher.launch(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, adapter::submitList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                productViewModel.delete(adapter.getProductAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, R.string.product_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(product -> {
            Intent intent = new Intent(MainActivity.this, AddEditProductActivity.class);
            intent.putExtra(AddEditProductActivity.EXTRA_ID, product.getId());
            intent.putExtra(AddEditProductActivity.EXTRA_NAME, product.getName());
            intent.putExtra(AddEditProductActivity.EXTRA_QUANTITY, product.getQuantity());
            intent.putExtra(AddEditProductActivity.EXTRA_PRICE, product.getPrice());
            intent.putExtra(AddEditProductActivity.EXTRA_DESCRIPTION, product.getDescription());
            editProductLauncher.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
