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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ListView listOfLists = (ListView) findViewById(R.id.list_item);
        TextView textView = new TextView(this);
        listOfLists.setAdapter(new ArrayAdapter<String> (this, R.layout.row, new ArrayList<String>()));

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
