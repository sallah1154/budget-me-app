package com.example.budgetme;
import java.util.Date;
import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String fromCategory(Category category){
        if(category == null ) return null;
        return category.getName() + ":" + category.getIconName();
    }


    @TypeConverter
    public static Category toCategory(String data){
        if(data == null) return null;
        String[] parts = data.split(":");
        return new Category(parts[0],parts[1]);

    }

    @TypeConverter
    public static Long fromDate(Date date){
        if(date == null) return null;
        return date.getTime();

    }

    @TypeConverter
    public static Date toDate(Long timeStamp){
        if(timeStamp==null) return null;
        return new Date(timeStamp);


    }
}
