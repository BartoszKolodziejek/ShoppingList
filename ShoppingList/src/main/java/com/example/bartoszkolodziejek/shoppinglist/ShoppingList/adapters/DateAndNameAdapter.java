package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        values.put(new Date(), object.toString());
        super.add(object);
    }

    public Object getItemByDate(Date date){
        return values.get(date);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        List<Map.Entry<Date, Object>> values = new ArrayList<>(this.values.entrySet());
        name.setText(values.get(position).getValue().toString());
        date.setText(dateFormat.format(values.get(position).getKey()));

        return rowView;
    }
}
