package com.example.inventorymanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaleAdapter extends ListAdapter<Sale, SaleAdapter.SaleHolder> {

    public SaleAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Sale> DIFF_CALLBACK = new DiffUtil.ItemCallback<Sale>() {
        @Override
        public boolean areItemsTheSame(@NonNull Sale oldItem, @NonNull Sale newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Sale oldItem, @NonNull Sale newItem) {
            return oldItem.getProductName().equals(newItem.getProductName()) &&
                    oldItem.getQuantity() == newItem.getQuantity() &&
                    oldItem.getTotalPrice() == newItem.getTotalPrice() &&
                    oldItem.getTimestamp() == newItem.getTimestamp() &&
                    oldItem.getUnitPrice() == newItem.getUnitPrice();
        }
    };

    @NonNull
    @Override
    public SaleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sale_item, parent, false);
        return new SaleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleHolder holder, int position) {
        Sale currentSale = getItem(position);
        holder.textViewProduct.setText(currentSale.getProductName());
        holder.textViewPrice.setText(String.format(Locale.getDefault(), "KSh %.2f", currentSale.getTotalPrice()));
        
        // Showing quantity with unit (e.g., 1.50 kg)
        holder.textViewQty.setText(String.format(Locale.getDefault(), "%.2f %s @ KSh %.2f",
            currentSale.getQuantity(), 
            currentSale.getUnit() != null ? currentSale.getUnit() : "kg",
            currentSale.getUnitPrice()));
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.textViewTime.setText(sdf.format(new Date(currentSale.getTimestamp())));
    }

    public static class SaleHolder extends RecyclerView.ViewHolder {
        private final TextView textViewProduct;
        private final TextView textViewPrice;
        private final TextView textViewQty;
        private final TextView textViewTime;

        public SaleHolder(@NonNull View itemView) {
            super(itemView);
            textViewProduct = itemView.findViewById(R.id.textViewSaleProduct);
            textViewPrice = itemView.findViewById(R.id.textViewSalePrice);
            textViewQty = itemView.findViewById(R.id.textViewSaleQty);
            textViewTime = itemView.findViewById(R.id.textViewSaleTime);
        }
    }
}
