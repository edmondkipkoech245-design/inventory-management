package com.example.inventorymanagement;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sales")
public class Sale {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int productId;
    private String productName;
    private double quantity; // Changed to double for KG support
    private String unit; // e.g., "kg", "pcs"
    private double unitPrice;
    private double totalPrice;
    private long timestamp;

    public Sale(int productId, String productName, double quantity, String unit, double unitPrice, double totalPrice, long timestamp) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
