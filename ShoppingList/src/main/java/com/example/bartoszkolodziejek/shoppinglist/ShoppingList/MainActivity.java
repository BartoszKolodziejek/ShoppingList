package com.example.bartoszkolodziejek.shoppinglist.ShoppingList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters.DateAndNameAdapter;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.Product;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.listeners.ClickListOfListListener;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.listeners.LongClickListOfListsListener;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import android.view.ViewGroup.LayoutParams;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        setContentView(R.layout.activity_main);
        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
        }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        final ListView listOfLists = (ListView) findViewById(R.id.list_item);
        TextView textView = new TextView(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        List<ShoppingLists> shoppingLists = ShoppingLists.listAll(ShoppingLists.class, "id");
        Map<Date, Object> shoppingListsMap = new HashMap<>();
        for (ShoppingLists shoppingList : shoppingLists){
            shoppingListsMap.put(shoppingList.getDate(), shoppingList);
        }
        listOfLists.setAdapter(new DateAndNameAdapter<ShoppingLists>(this, shoppingListsMap, dateFormat));
        final LongClickListOfListsListener longClickListOfListsListener = new LongClickListOfListsListener(listOfLists, getApplicationContext());
        listOfLists.setOnItemLongClickListener(longClickListOfListsListener);
        listOfLists.setOnItemClickListener(new ClickListOfListListener(getApplicationContext(), longClickListOfListsListener, this, listOfLists));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final ListView listOfLists = (ListView) findViewById(R.id.list_item);
        TextView textView = new TextView(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        List<ShoppingLists> shoppingLists = ShoppingLists.listAll(ShoppingLists.class, "id");
        Map<Date, Object> shoppingListsMap = new HashMap<>();
        for (ShoppingLists shoppingList : shoppingLists){
            shoppingListsMap.put(shoppingList.getDate(), shoppingList);
        }
        listOfLists.setAdapter(new DateAndNameAdapter<ShoppingLists>(this, shoppingListsMap, dateFormat));
        final LongClickListOfListsListener longClickListOfListsListener = new LongClickListOfListsListener(listOfLists, getApplicationContext());
        listOfLists.setOnItemLongClickListener(longClickListOfListsListener);
        listOfLists.setOnItemClickListener(new ClickListOfListListener(getApplicationContext(), longClickListOfListsListener, this, listOfLists));

    }

    public void add(View view){
        ListView listOfLists = (ListView) findViewById(R.id.list_item);
        EditText listNameField = (EditText) findViewById(R.id.list_name);
        String name = listNameField.getText().toString();
        listNameField.getText().clear();
        DateAndNameAdapter<ShoppingLists> listAdapter = (DateAndNameAdapter<ShoppingLists>) listOfLists.getAdapter();


        ShoppingLists shoppingLists = new ShoppingLists(name, new Date());
        listAdapter.add(shoppingLists);
        shoppingLists.save();



    }


}
