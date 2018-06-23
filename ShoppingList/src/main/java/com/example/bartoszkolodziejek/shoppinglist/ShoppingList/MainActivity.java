package com.example.bartoszkolodziejek.shoppinglist.ShoppingList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters.DateAndNameAdapter;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ListView listOfLists = (ListView) findViewById(R.id.list_item);
        TextView textView = new TextView(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        List<ShoppingLists> shoppingLists = ShoppingLists.listAll(ShoppingLists.class);
        Map<Date, Object> shoppingListsMap = new HashMap<>();
        for (ShoppingLists shoppingList : shoppingLists){
            shoppingListsMap.put(shoppingList.getDate(), shoppingList);
        }

        listOfLists.setAdapter(new DateAndNameAdapter<ShoppingLists>(this, shoppingListsMap, dateFormat));

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
