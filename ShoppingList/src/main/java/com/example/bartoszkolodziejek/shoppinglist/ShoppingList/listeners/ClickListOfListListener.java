package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.ListActivity;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters.DateAndNameAdapter;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;

public class ClickListOfListListener implements AdapterView.OnItemClickListener {

    private final Context context;
    private final LongClickListOfListsListener longClickListOfListsListener;
    private final AppCompatActivity appCompatActivity;
    private final ListView listView;

    public ClickListOfListListener(Context context, LongClickListOfListsListener longClickListOfListsListener, AppCompatActivity appCompatActivity, ListView listView) {
        this.context = context;
        this.longClickListOfListsListener = longClickListOfListsListener;
        this.appCompatActivity = appCompatActivity;
        this.listView = listView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent list = new Intent(context, ListActivity.class);
        final Bundle bundle = new Bundle();
        ShoppingLists shoppingList = (ShoppingLists) ((DateAndNameAdapter) listView.getAdapter()).getItem(i);
        list.putExtra("list", shoppingList);
        if(longClickListOfListsListener.isIntentAcceptable())
            appCompatActivity.startActivity(list);
    }
}

