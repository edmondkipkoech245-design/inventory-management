package com.example.inventorymanagement;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String workerId;
    private String password;

    public User(@NonNull String workerId, String password) {
        this.workerId = workerId;
        this.password = password;
    }

    @NonNull
    public String getWorkerId() { return workerId; }
    public void setWorkerId(@NonNull String workerId) { this.workerId = workerId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
