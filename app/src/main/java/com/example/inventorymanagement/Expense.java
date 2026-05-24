package com.example.inventorymanagement;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private double amount;
    private long timestamp;

    public Expense(String description, double amount, long timestamp) {
        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
