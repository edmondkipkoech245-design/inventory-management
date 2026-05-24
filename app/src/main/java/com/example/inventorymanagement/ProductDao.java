package com.example.inventorymanagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products ORDER BY name ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE id = :id")
    Product getProductById(int id);

    @Query("SELECT COUNT(*) FROM products")
    LiveData<Integer> getProductCount();

    @Query("SELECT SUM(quantity) FROM products")
    LiveData<Integer> getTotalQuantity();

    @Query("SELECT COUNT(*) FROM products WHERE quantity < 5")
    LiveData<Integer> getLowStockCount();
}
