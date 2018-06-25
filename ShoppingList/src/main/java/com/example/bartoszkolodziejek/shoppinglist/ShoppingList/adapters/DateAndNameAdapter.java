package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.R;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ProductsList;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DateAndNameAdapter<T> extends ArrayAdapter {



    private Map<Date, Object> values;
    private SimpleDateFormat dateFormat;

    private final Context context;


    public DateAndNameAdapter(@NonNull Context context, @NonNull Map<Date, Object> values, SimpleDateFormat dateFormat) {
        super(context, -1, new ArrayList(values.values()));
        this.context = context;
        this.values = values;
        this.dateFormat = dateFormat;

    }

    @Override
    public void add(@Nullable Object object) {

        values.put(new Date(), object);
        super.add(object);
    }

    public Object getItemByDate(Date date){
        return values.get(date);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return new ArrayList<>(values.values()).get(position);
    }

    @Override
    public void remove(@Nullable Object object) {
        super.remove(object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView date = (TextView) rowView.findViewById(R.id.date);

        TextView amount = (TextView) rowView.findViewById(R.id.items);
        TextView status = (TextView) rowView.findViewById(R.id.status);
        List<Map.Entry<Date, Object>> values = new ArrayList<>(this.values.entrySet());
        String[] params = {((ShoppingLists) values.get(position).getValue()).getId().toString()};
        long am = ProductsList.count(ProductsList.class, "shopping_lists = ?",params);
        List<String> arrayList = new ArrayList<>();
        arrayList.add(params[0]);
        arrayList.add("1");
        String[] params2 = new String[2];
        arrayList.toArray(params2);
        long bought =  ProductsList.count(ProductsList.class, "shopping_lists = ? and realised = ?", params2);

        name.setText(values.get(position).getValue().toString());
        date.setText(dateFormat.format(values.get(position).getKey()));
        amount.setText(String.valueOf(am));
        status.setText(getStatus(am, bought));
        return rowView;
    }

    private String getStatus(long products, long bought){
        if(bought==0)
            return context.getResources().getString(R.string.new_list);
        if(bought/products==0)
            return context.getResources().getString(R.string.partly);
        return context.getResources().getString(R.string.realised);
    }
}
