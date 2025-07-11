package com.example.budgetme;

import java.util.ArrayList;
import java.util.List;

public class Categorymanager {
    private List<Category> categories;

    public Categorymanager(){
        this.categories = new ArrayList<>();
        initializeDefaultCategories();
    }
    private void initializeDefaultCategories() {
        //TODO: add default categories
    }
     public void addCategory(Category category){
        categories.add(category);
     }
     public boolean removeCategory(String categoryName){
         return categories.removeIf(category -> category.getName().equals(categoryName));
     }
}
