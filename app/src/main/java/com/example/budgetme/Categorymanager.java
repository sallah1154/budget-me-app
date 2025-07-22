package com.example.budgetme;

import java.util.ArrayList;
import java.util.List;

public class Categorymanager {
    private List<Category> categories;

    public Categorymanager() {
        this.categories = new ArrayList<>();
        initializeDefaultCategories();
    }

    private void initializeDefaultCategories() {
        categories.add(new Category("Food", "ic_food"));
        categories.add(new Category("Transport", "ic_transport"));
        categories.add(new Category("Entertainment", "ic_entertainment"));
        categories.add(new Category("Bills", "ic_bills"));
        categories.add(new Category("Salary", "ic_salary"));
        categories.add(new Category("Other", "ic_other"));
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public boolean removeCategory(String categoryName) {
        return categories.removeIf(category -> category.getName().equals(categoryName));
    }

    public List<Category> getCategories() {
        return categories;
    }
}