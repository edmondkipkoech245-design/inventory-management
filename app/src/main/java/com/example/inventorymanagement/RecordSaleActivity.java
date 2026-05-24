package com.example.inventorymanagement;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RecordSaleActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    private Product selectedProduct;
    private AutoCompleteTextView autoCompleteProduct;
    private TextInputEditText editTextSaleQuantity;
    private TextInputEditText editTextUnitPrice;
    private TextView textViewTotalCost;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_sale);

        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        autoCompleteProduct = findViewById(R.id.autoCompleteProduct);
        editTextSaleQuantity = findViewById(R.id.editTextSaleQuantity);
        editTextUnitPrice = findViewById(R.id.editTextUnitPrice);
        textViewTotalCost = findViewById(R.id.textViewTotalCost);
        Button buttonConfirmSale = findViewById(R.id.buttonConfirmSale);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, products -> {
            this.productList = products;
            List<String> productNames = new ArrayList<>();
            for (Product p : products) {
                productNames.add(p.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, productNames);
            autoCompleteProduct.setAdapter(adapter);
        });

        autoCompleteProduct.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = (String) parent.getItemAtPosition(position);
            for (Product p : productList) {
                if (p.getName().equals(selectedName)) {
                    selectedProduct = p;
                    editTextUnitPrice.setText(String.format(Locale.getDefault(), "%.2f", p.getPrice()));
                    calculateTotal();
                    break;
                }
            }
        });

        TextWatcher calculationWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTextSaleQuantity.addTextChangedListener(calculationWatcher);
        editTextUnitPrice.addTextChangedListener(calculationWatcher);

        buttonConfirmSale.setOnClickListener(v -> confirmSale());
    }

    private void calculateTotal() {
        String qtyStr = Objects.requireNonNull(editTextSaleQuantity.getText()).toString();
        String priceStr = Objects.requireNonNull(editTextUnitPrice.getText()).toString();

        if (!qtyStr.isEmpty() && !priceStr.isEmpty()) {
            try {
                double qty = Double.parseDouble(qtyStr);
                double price = Double.parseDouble(priceStr);
                double total = qty * price;
                textViewTotalCost.setText(String.format(Locale.getDefault(), "KSh %.2f", total));
            } catch (NumberFormatException e) {
                textViewTotalCost.setText("KSh 0.00");
            }
        } else {
            textViewTotalCost.setText("KSh 0.00");
        }
    }

    private void confirmSale() {
        if (selectedProduct == null) {
            Toast.makeText(this, R.string.error_select_product, Toast.LENGTH_SHORT).show();
            return;
        }

        String qtyStr = Objects.requireNonNull(editTextSaleQuantity.getText()).toString().trim();
        String priceStr = Objects.requireNonNull(editTextUnitPrice.getText()).toString().trim();
        
        if (qtyStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double saleQty = Double.parseDouble(qtyStr);
            double unitPrice = Double.parseDouble(priceStr);

            if (saleQty <= 0) {
                Toast.makeText(this, R.string.error_invalid_quantity, Toast.LENGTH_SHORT).show();
                return;
            }

            if (saleQty > selectedProduct.getQuantity()) {
                Toast.makeText(this, R.string.error_no_stock, Toast.LENGTH_SHORT).show();
                return;
            }

            double totalPrice = saleQty * unitPrice;
            
            // Record Sale with KG support
            Sale sale = new Sale(
                    selectedProduct.getId(), 
                    selectedProduct.getName(), 
                    saleQty, 
                    "kg", 
                    unitPrice, 
                    totalPrice, 
                    System.currentTimeMillis()
            );
            productViewModel.insertSale(sale);

            // Update Product Stock (casting to int for simplicity if inventory is int-based)
            selectedProduct.setQuantity(selectedProduct.getQuantity() - (int)saleQty);
            productViewModel.update(selectedProduct);

            Toast.makeText(this, R.string.sale_recorded, Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
