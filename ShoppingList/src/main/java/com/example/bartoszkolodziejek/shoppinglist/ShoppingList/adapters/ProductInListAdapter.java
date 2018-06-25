package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.R;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ProductsList;

import java.util.List;


public class ProductInListAdapter<T> extends ArrayAdapter {


    private List<ProductsList> values;


    private final Context context;


    public ProductInListAdapter(@NonNull Context context, @NonNull List<ProductsList> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;


    }

    @Override
    public void add(@Nullable Object object) {
        values.add((ProductsList) object);

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopping_list_row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView unit = (TextView) rowView.findViewById(R.id.unit);
        TextView amount = (TextView) rowView.findViewById(R.id.amount);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.realised);
        name.setText(values.get(position).getProduct().getName());
        unit.setText(values.get(position).getProduct().getUnit());
        amount.setText(values.get(position).getValue().toString());
        checkBox.setChecked(values.get(position).getRealised());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                values.get(position).setRealised(b);
                values.get(position).save();
            }
        });



        return rowView;
    }
}