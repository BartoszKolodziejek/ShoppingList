package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DateAndNameAdapter<T> extends ArrayAdapter {


    private List<Date> dates = new ArrayList<>();
    private List<String> values;
    private SimpleDateFormat dateFormat;

    private final Context context;


    public DateAndNameAdapter(@NonNull Context context, @NonNull List<String> values, SimpleDateFormat dateFormat) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.dateFormat = dateFormat;

    }

    @Override
    public void add(@Nullable Object object) {
        dates.add(new Date());
        super.add(object);
    }

    public T getItemByDate(@Nullable Date date){
        return (T)(super.getItem(dates.indexOf(date)));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        name.setText(values.get(position));
        date.setText(dateFormat.format(dates.get(position)));

        return rowView;
    }
}
