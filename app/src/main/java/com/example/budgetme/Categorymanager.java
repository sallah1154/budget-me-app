package com.example.budgetme;

import java.util.ArrayList;
import java.util.List;

public class Categorymanager {
    private List<Category> categories;
    private static Categorymanager instance;

    public Categorymanager(){
        this.categories = new ArrayList<>();
        initializeDefaultCategories();
    }
    public static synchronized Categorymanager getInstance() {
        if (instance == null) {
            instance = new Categorymanager();
        }
        return instance;
    }
    private void initializeDefaultCategories() {
        //TODO: add default categories
//        categories.add(new Category("Add Transaction", "ic_money"));
        categories.add(new Category("Housing", "ic_mortgage"));
        categories.add(new Category("Utilities", "ic_piping"));
        categories.add(new Category("Shopping", "ic_shopping"));
        categories.add(new Category("Food & Dining", "ic_pizza"));
        categories.add(new Category("Healthcare", "ic_person_at_home"));
        categories.add(new Category("Travel", "ic_car"));
        categories.add(new Category("Insurance", "ic_guarantee"));
        categories.add(new Category("Miscellaneous", "ic_categorize"));
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
