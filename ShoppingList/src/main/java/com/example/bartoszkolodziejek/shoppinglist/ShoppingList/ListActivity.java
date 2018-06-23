package com.example.bartoszkolodziejek.shoppinglist.ShoppingList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.Product;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView = (TextView) findViewById(R.id.TITLE);
        ShoppingLists list = (ShoppingLists) getIntent().getSerializableExtra("list");
        textView.setText(list.getName());
    }


    public void expandItemLayout(View view){

        final ImageView imageView = (ImageView) findViewById(R.id.button);
        imageView.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View productList = inflater.inflate(R.layout.product_choose_layout, null);
        final Spinner items = (Spinner) productList.findViewById(R.id.products);

        List<Product> products = Product.listAll(Product.class);
        List<String> values = new ArrayList<>();
        values.add(getResources().getString(R.string.selectItem));
        values.add(getResources().getString(R.string.addItem));
        for (Product product : products){
            values.add(product.getName());
        }

        final PopupWindow popupWindow = new PopupWindow(productList,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView collapse = (ImageView) productList.findViewById(R.id.collapse);
        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                imageView.setVisibility(View.VISIBLE);
            }
        });


        Button accept = (Button) productList.findViewById(R.id.accept);
        items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = items.getSelectedItemPosition();
                if (pos == 1) {
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    startActivity(intent);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item_layout , values);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        items.setAdapter(arrayAdapter);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);


    }
}
