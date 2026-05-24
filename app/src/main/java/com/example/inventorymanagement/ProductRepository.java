package com.example.inventorymanagement;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao mProductDao;
    private SaleDao mSaleDao;
    private ExpenseDao mExpenseDao;
    private LiveData<List<Product>> mAllProducts;

    ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mSaleDao = db.saleDao();
        mExpenseDao = db.expenseDao();
        mAllProducts = mProductDao.getAllProducts();
    }

    void insertExpense(Expense expense) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mExpenseDao.insert(expense);
        });
    }

    LiveData<List<Expense>> getAllExpenses() {
        return mExpenseDao.getAllExpenses();
    }

    LiveData<Double> getTotalExpensesSince(long startTime) {
        return mExpenseDao.getTotalExpensesSince(startTime);
    }

    LiveData<Double> getTotalSalesBetween(long startTime, long endTime) {
        return mSaleDao.getTotalSalesBetween(startTime, endTime);
    }

    LiveData<Double> getTotalExpensesBetween(long startTime, long endTime) {
        return mExpenseDao.getTotalExpensesBetween(startTime, endTime);
    }

    LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.insert(product);
        });
    }

    void update(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.update(product);
        });
    }

    void delete(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.delete(product);
        });
    }

    LiveData<Integer> getProductCount() {
        return mProductDao.getProductCount();
    }

    LiveData<Integer> getTotalQuantity() {
        return mProductDao.getTotalQuantity();
    }

    LiveData<Integer> getLowStockCount() {
        return mProductDao.getLowStockCount();
    }

    void insertSale(Sale sale) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mSaleDao.insert(sale);
        });
    }

    LiveData<Double> getTotalSalesSince(long startTime) {
        return mSaleDao.getTotalSalesSince(startTime);
    }

    LiveData<List<Sale>> getSalesSince(long startTime) {
        return mSaleDao.getSalesSince(startTime);
    }
}
