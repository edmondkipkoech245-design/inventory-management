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

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseHolder> {

    public ExpenseAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Expense> DIFF_CALLBACK = new DiffUtil.ItemCallback<Expense>() {
        @Override
        public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
            return oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getAmount() == newItem.getAmount();
        }
    };

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currentExpense = getItem(position);
        holder.textViewDescription.setText(currentExpense.getDescription());
        holder.textViewAmount.setText(String.format(Locale.getDefault(), "KSh %.2f", currentExpense.getAmount()));
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
        holder.textViewDate.setText(sdf.format(new Date(currentExpense.getTimestamp())));
    }

    public static class ExpenseHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDescription;
        private final TextView textViewAmount;
        private final TextView textViewDate;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textViewExpenseDescription);
            textViewAmount = itemView.findViewById(R.id.textViewExpenseAmount);
            textViewDate = itemView.findViewById(R.id.textViewExpenseDate);
        }
    }
}
