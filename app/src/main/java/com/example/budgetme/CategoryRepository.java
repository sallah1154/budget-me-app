package com.example.budgetme;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private final LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        appDatabase db = appDatabase.getDatabase(application);
        categoryDAO = db.categoryDao();
        allCategories = categoryDAO.getAllCategory();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) {
        appDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.insertCategory(category);
        });
    }

    public void delete(Category category) {
        appDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.deleteCategory(category);
        });
    }
}