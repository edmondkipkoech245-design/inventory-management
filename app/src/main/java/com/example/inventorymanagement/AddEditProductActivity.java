package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddEditProductActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.inventorymanagement.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.inventorymanagement.EXTRA_NAME";
    public static final String EXTRA_QUANTITY = "com.example.inventorymanagement.EXTRA_QUANTITY";
    public static final String EXTRA_PRICE = "com.example.inventorymanagement.EXTRA_PRICE";
    public static final String EXTRA_DESCRIPTION = "com.example.inventorymanagement.EXTRA_DESCRIPTION";

    private TextInputEditText editTextName;
    private TextInputEditText editTextQuantity;
    private TextInputEditText editTextPrice;
    private TextInputEditText editTextDescription;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        Button buttonSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(R.string.edit_product);
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextQuantity.setText(String.valueOf(intent.getIntExtra(EXTRA_QUANTITY, 1)));
            editTextPrice.setText(String.valueOf(intent.getDoubleExtra(EXTRA_PRICE, 0.0)));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle(R.string.add_product);
        }

        buttonSave.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        Editable nameEdit = editTextName.getText();
        Editable qtyEdit = editTextQuantity.getText();
        Editable priceEdit = editTextPrice.getText();
        Editable descEdit = editTextDescription.getText();

        String name = (nameEdit != null) ? nameEdit.toString().trim() : "";
        String quantityStr = (qtyEdit != null) ? qtyEdit.toString().trim() : "";
        String priceStr = (priceEdit != null) ? priceEdit.toString().trim() : "";
        String description = (descEdit != null) ? descEdit.toString().trim() : "";

        if (name.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);

        Product product = new Product(name, quantity, price, description);
        
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            product.setId(id);
            productViewModel.update(product);
            Toast.makeText(this, R.string.product_updated, Toast.LENGTH_SHORT).show();
        } else {
            productViewModel.insert(product);
            Toast.makeText(this, R.string.product_saved, Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
