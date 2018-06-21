package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.helpers;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.MainActivity;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.annotaitions.Column;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.throwables.WrongMappingException;
import com.google.common.collect.Lists;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

public class EntityHelper {
    private final SQLiteDatabase db;


    public EntityHelper(SQLiteDatabase db) {
        this.db = db;
    }


    private Set<Class<?extends Object>> getEntities() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Class.forName("android.app.ActivityThread");
        Method method  = clazz.getDeclaredMethod("currentPackageName", null);
        String appPackageName = (String) method.invoke(clazz, null);
        appPackageName = appPackageName.replace("app", "ShoppingList");
        String entityPackageName = appPackageName+".entities";
        Reflections reflections = new Reflections(entityPackageName);
        return reflections.getSubTypesOf(Object.class);

    }

    public void createEntities() throws WrongMappingException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            for (Class clasz : getEntities()){
                Reflections reflections = new Reflections(clasz);
                List<Field> fields = Lists.newArrayList(reflections.getFieldsAnnotatedWith(Column.class));
                QueryBuilder queryBuilder = new QueryBuilder();
                Log.d("qeuery", queryBuilder.createTable(clasz.getName(), fields));
                db.execSQL(queryBuilder.createTable(clasz.getName(), fields));

            }

    }
}
