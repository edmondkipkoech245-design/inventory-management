package com.example.inventorymanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductHolder> {
    private OnItemClickListener listener;

    public ProductAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getQuantity() == newItem.getQuantity() &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = getItem(position);
        holder.textViewName.setText(currentProduct.getName());
        holder.textViewQuantity.setText(String.format(Locale.getDefault(), "Qty: %d", currentProduct.getQuantity()));
        holder.textViewPrice.setText(String.format(Locale.getDefault(), "KSh %.2f", currentProduct.getPrice()));
    }

    public Product getProductAt(int position) {
        return getItem(position);
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewQuantity;
        private final TextView textViewPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
