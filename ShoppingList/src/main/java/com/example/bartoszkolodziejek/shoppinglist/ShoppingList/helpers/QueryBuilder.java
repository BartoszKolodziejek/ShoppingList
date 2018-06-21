package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.helpers;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.throwables.WrongMappingException;

import java.lang.reflect.Field;
import java.util.List;

public class QueryBuilder {


    public String createTable(String name, List<Field> columns) throws WrongMappingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table ");
        stringBuilder.append(name);
        stringBuilder.append("(");
        for (Field field : columns){
            if(columns.indexOf(field)!=columns.size())
            stringBuilder.append(field.getName()+" "+getSQLiteTypeFromJavaType(field)+",");
            else
                stringBuilder.append(field.getName()+" "+getSQLiteTypeFromJavaType(field));
        }
        stringBuilder.append(")");
        stringBuilder.append(";");
        return stringBuilder.toString();


    }


    private String getSQLiteTypeFromJavaType(Field field) throws WrongMappingException{
        String type = field.getType().getName();
        switch (type){
            case "String":
                return type;
            case "Integer":
                if(field.getName().equalsIgnoreCase("id"))
                    type = type+" Primary Key Autoincrement ";
                return type;
            default: throw new WrongMappingException("the type "+type+" is not supported");
        }

    }
}
