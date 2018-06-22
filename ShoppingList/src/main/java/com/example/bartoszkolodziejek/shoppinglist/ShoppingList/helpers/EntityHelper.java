package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.annotaitions.Column;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.annotaitions.Entity;
import com.example.bartoszkolodziejek.shoppinglist.ShoppingList.throwables.WrongMappingException;
import com.google.common.collect.Lists;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;


public class EntityHelper {
    private final SQLiteDatabase db;


    public EntityHelper(SQLiteDatabase db) {
        this.db = db;
    }


    private Set<Class<?extends Object>> getEntities(Context context) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, IOException {
        Class<?> clazz = Class.forName("android.app.ActivityThread");
        Method method  = clazz.getDeclaredMethod("currentPackageName", null);
        String appPackageName = (String) method.invoke(clazz, null);
        appPackageName = appPackageName.replace("app", "ShoppingList");
        String entityPackageName = appPackageName+".entities";
        FilterBuilder filters =  (new FilterBuilder()).include(entityPackageName);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setUrls((new ReflectionHelper()).getUrls(context));
        configurationBuilder.filterInputsBy(filters);
        Reflections reflections = new Reflections(configurationBuilder);
        return reflections.getTypesAnnotatedWith(Entity.class);

    }

    public void createEntities(Context context) throws WrongMappingException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, IOException {
            for (Class clasz : getEntities(context)){
                Reflections reflections = new Reflections(clasz);
                List<Field> fields = Lists.newArrayList(reflections.getFieldsAnnotatedWith(Column.class));
                QueryBuilder queryBuilder = new QueryBuilder();
                Log.d("qeuery", queryBuilder.createTable(clasz.getName(), fields));
                db.execSQL(queryBuilder.createTable(clasz.getName(), fields));

            }

    }
}
