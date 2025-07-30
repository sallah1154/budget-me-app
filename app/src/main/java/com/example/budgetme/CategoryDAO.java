package com.example.budgetme;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllCategory();



    @Insert
    void insertCategory(Category category );

    @Delete
    void deleteCategory(Category category);
}
