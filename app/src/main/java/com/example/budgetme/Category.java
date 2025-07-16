package com.example.budgetme;

public class Category {
    private String name;
    private String iconName;

    public Category(String name,  String iconName){
        this.name = name;
        this.iconName = iconName;
    }
    public String getName() {return name;}
    public String getIconName() {return iconName;}

    @Override
    public String toString(){
        return name;
    }
}
