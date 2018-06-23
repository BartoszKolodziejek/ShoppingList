package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.listeners;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.R;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.adapters.DateAndNameAdapter;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.entities.ShoppingLists;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LongClickListOfListsListener implements AdapterView.OnItemLongClickListener {
    private ListView listOfLists;
    private Context context;
    private boolean intentAcceptable = true;

    public boolean isIntentAcceptable() {
        return intentAcceptable;
    }

    public LongClickListOfListsListener(ListView listOfLists, Context context) {
        this.listOfLists = listOfLists;
        this.context = context;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        intentAcceptable = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.pop_up_window,null);
        TextView message = (TextView) popUpView.findViewById(R.id.message);
        message.setText(R.string.deleteList);

        final PopupWindow popupWindow = new PopupWindow(popUpView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setElevation(5.0f);

        Button accept = (Button) popUpView.findViewById(R.id.confirm);
        accept.setText(R.string.yes);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingLists deletingShoppingList = (ShoppingLists) ((DateAndNameAdapter) listOfLists.getAdapter()).getItem(i);
                deletingShoppingList.delete();
                ((DateAndNameAdapter) listOfLists.getAdapter()).remove( listOfLists.getAdapter().getItem(i));
                popupWindow.dismiss();
                intentAcceptable = true;
            }
        });

        Button reject = (Button) popUpView.findViewById(R.id.reject);
        reject.setText(R.string.no);

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                intentAcceptable = true;
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        return false;
    }
}



