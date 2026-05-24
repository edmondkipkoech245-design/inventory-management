package com.example.inventorymanagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SaleDao {
    @Insert
    void insert(Sale sale);

    @Query("SELECT * FROM sales ORDER BY timestamp DESC")
    LiveData<List<Sale>> getAllSales();

    @Query("SELECT SUM(totalPrice) FROM sales WHERE timestamp >= :startTime")
    LiveData<Double> getTotalSalesSince(long startTime);

    @Query("SELECT SUM(totalPrice) FROM sales WHERE timestamp >= :startTime AND timestamp < :endTime")
    LiveData<Double> getTotalSalesBetween(long startTime, long endTime);

    @Query("SELECT * FROM sales WHERE timestamp >= :startTime ORDER BY timestamp DESC")
    LiveData<List<Sale>> getSalesSince(long startTime);
}
