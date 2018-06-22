package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.MainActivity;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.throwables.WrongMappingException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME ="shoplist.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        EntityHelper entityHelper = new EntityHelper(sqLiteDatabase);
        try {

            entityHelper.createEntities(MainActivity.getContext());
        } catch (WrongMappingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
