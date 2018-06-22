package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.annotaitions.Column;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.annotaitions.Entity;

import java.util.Date;


@Entity
public class ShoppingLists {
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private Date date;

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public ShoppingLists(String name, Date date) {
        this.name = name;
        this.date = date;
    }
}
