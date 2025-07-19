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
//        categories.add(new Category("Add Transaction", "ic_money"));
        categories.add(new Category("Rent", "ic_mortgage"));
        categories.add(new Category("Phone Bill", "ic_phone_squared"));
        categories.add(new Category("Water Bill", "ic_piping"));
        categories.add(new Category("Groceries", "ic_shopping"));
        categories.add(new Category("Car Payment", "ic_car"));

    }
     public void addCategory(Category category){
        categories.add(category);
     }
     public boolean removeCategory(String categoryName){
         return categories.removeIf(category -> category.getName().equals(categoryName));
     }

     public List<Category> getCategories(){
        return categories;
     }
}
