package com.example.inventorymanagement;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM users WHERE workerId = :workerId LIMIT 1")
    User getUser(String workerId);

    @Update
    void update(User user);

    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();
}
