package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.io.Serializable;
@Table
public class Product extends SugarRecord implements Serializable {
    @Unique
    private String name;
    private String unit;
    private String photo;

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getPhoto() {
        return photo;
    }

    public Product(String name, String unit, String photo) {
        this.name = name;
        this.unit = unit;
        this.photo = photo;
    }
    public Product(){

    }

    @Override
    public String toString() {
        return  name ;
    }
}
