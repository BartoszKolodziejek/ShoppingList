package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.List;
@Table
public class ProductsList extends SugarRecord implements Serializable {


    private Product product;
    private Boolean realised;
    private Float value;
    private ShoppingLists shoppingLists;


    public ProductsList( Product product, Boolean realised, Float value, ShoppingLists shoppingLists) {

        this.product = product;
        this.realised = realised;
        this.value = value;
        this.shoppingLists = shoppingLists;
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

    public void setRealised(Boolean realised) {
        this.realised = realised;
    }

    public ShoppingLists getShoppingLists() {
        return shoppingLists;
    }
}
