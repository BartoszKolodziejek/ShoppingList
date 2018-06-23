package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities;





import com.orm.SugarRecord;

import java.util.Date;
import java.util.Objects;


public class ShoppingLists  extends SugarRecord {



    private String name;

    private Date date;

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ShoppingLists(){}
    public ShoppingLists(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingLists)) return false;
        ShoppingLists that = (ShoppingLists) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, date);
    }
}
