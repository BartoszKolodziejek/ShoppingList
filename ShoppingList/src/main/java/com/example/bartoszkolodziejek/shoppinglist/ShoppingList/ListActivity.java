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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters.ProductInListAdapter;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.Product;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ProductsList;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.listeners.LongClickListListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    protected ListView listView;
    protected ShoppingLists list;
    ProductInListAdapter<ProductsList> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView = (TextView) findViewById(R.id.TITLE);
        this.list = ShoppingLists.findById(ShoppingLists.class, getIntent().getLongExtra("id", 0));
        textView.setText(list.getName());
        ListView listView = (ListView) findViewById(R.id.list_item);
        List<ProductsList> productsLists = ProductsList.find(ProductsList.class, "SHOPPING_LISTS = "+list.getId());
        adapter = new ProductInListAdapter<ProductsList>(this, productsLists);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new LongClickListListener(listView, getApplicationContext()));
        listView.setItemsCanFocus(true);

    }


    public void expandItemLayout(View view){

        final ImageView imageView = (ImageView) findViewById(R.id.button);

        imageView.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View productList = inflater.inflate(R.layout.product_choose_layout, null);
        final Spinner items = (Spinner) productList.findViewById(R.id.products);

        final List<Product> products = Product.listAll(Product.class);
        List<String> values = new ArrayList<>();
        values.add(getResources().getString(R.string.selectItem));
        values.add(getResources().getString(R.string.addItem));
        for (Product product : products){
            values.add(product.getName());
        }

        final PopupWindow popupWindow = new PopupWindow(productList,
                ViewGroup.LayoutParams.MATCH_PARENT,
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
                    popupWindow.dismiss();
                    imageView.setVisibility(View.VISIBLE);
                    startActivity(intent);
                    }

                else if (pos==0){
                    RelativeLayout amountView = (RelativeLayout) productList.findViewById(R.id.amountView);
                    RelativeLayout unitView = (RelativeLayout) productList.findViewById(R.id.unitView);
                    amountView.setVisibility(View.GONE);
                    unitView.setVisibility(View.GONE);

                }
                else {

                    RelativeLayout amountView = (RelativeLayout) productList.findViewById(R.id.amountView);
                    RelativeLayout unitView = (RelativeLayout) productList.findViewById(R.id.unitView);
                    amountView.setVisibility(View.VISIBLE);
                    unitView.setVisibility(View.VISIBLE);
                    TextView unit = (TextView) productList.findViewById(R.id.unit);
                    unit.setText(products.get(i-2).getUnit());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText amount = (EditText) productList.findViewById(R.id.amount);

                if(validate(amount.getText().toString(),productList)) {
                    Product product = Product.find(Product.class, "name = '"+items.getSelectedItem()+"'").get(0);
                    ProductsList productsList = new ProductsList(product, false,  new Float(Float.parseFloat(amount.getText().toString())),list );
                    productsList.save();

                    adapter.add(productsList);
                    imageView.setVisibility(View.VISIBLE);
                    popupWindow.dismiss();
                    }
                    else{
                    Toast toast = new Toast(getApplicationContext());
                    toast.setText(R.string.amountValidation);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item_layout , values);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        items.setAdapter(arrayAdapter);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        }

    private boolean validate(String s, View view) {
        TextView textView = (TextView) view.findViewById(R.id.unit);
        List<String> units= Arrays.asList(getResources().getStringArray(R.array.units));
        int unit = units.indexOf(textView.getText().toString());
        if(s.matches("\\d*")&&unit!=2)
            return true;
        if(s.matches("\\d*\\.\\d*"))
            return true;
        return false;


    }


}
