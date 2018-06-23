package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

public class ProductsList extends SugarRecord implements Serializable {


    private Product product;
    private Boolean realised;
    private Float value;


    public ProductsList( Product product, Boolean realised, Float value) {

        this.product = product;
        this.realised = realised;
        this.value = value;
    }
    public ProductsList(){}


    public Product getProduct() {
        return product;
    }

    public Boolean getRealised() {
        return realised;
    }

    public Float getValue() {
        return value;
    }
}
