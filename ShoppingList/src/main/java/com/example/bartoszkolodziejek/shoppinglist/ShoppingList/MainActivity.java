package com.example.bartoszkolodziejek.shoppinglist.ShoppingList;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters.DateAndNameAdapter;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.helpers.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);


    }




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ListView listOfLists = (ListView) findViewById(R.id.list_item);
        TextView textView = new TextView(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        listOfLists.setAdapter(new DateAndNameAdapter<String>(this, new ArrayList<String>(), dateFormat));

    }

    public void add(View view){
        ListView listOfLists = (ListView) findViewById(R.id.list_item);
        EditText listNameField = (EditText) findViewById(R.id.list_name);
        String name = listNameField.getText().toString();
        listNameField.getText().clear();
        ArrayAdapter<String> listAdapter = (ArrayAdapter<String>) listOfLists.getAdapter();

        listAdapter.add(name);



    }
}
