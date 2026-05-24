package com.example.inventorymanagement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private final LiveData<List<Product>> mAllProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    public void insert(Product product) {
        mRepository.insert(product);
    }

    public void update(Product product) {
        mRepository.update(product);
    }

    public void delete(Product product) {
        mRepository.delete(product);
    }

    public LiveData<Integer> getProductCount() {
        return mRepository.getProductCount();
    }

    public LiveData<Integer> getTotalQuantity() {
        return mRepository.getTotalQuantity();
    }

    public LiveData<Integer> getLowStockCount() {
        return mRepository.getLowStockCount();
    }

    public void insertSale(Sale sale) {
        mRepository.insertSale(sale);
    }

    public LiveData<Double> getTotalSalesSince(long startTime) {
        return mRepository.getTotalSalesSince(startTime);
    }

    public LiveData<List<Sale>> getSalesSince(long startTime) {
        return mRepository.getSalesSince(startTime);
    }

    public void insertExpense(Expense expense) {
        mRepository.insertExpense(expense);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return mRepository.getAllExpenses();
    }

    public LiveData<Double> getTotalExpensesSince(long startTime) {
        return mRepository.getTotalExpensesSince(startTime);
    }

    public LiveData<Double> getTotalSalesBetween(long startTime, long endTime) {
        return mRepository.getTotalSalesBetween(startTime, endTime);
    }

    public LiveData<Double> getTotalExpensesBetween(long startTime, long endTime) {
        return mRepository.getTotalExpensesBetween(startTime, endTime);
    }
}
