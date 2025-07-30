package com.example.budgetme;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "iconName")
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
