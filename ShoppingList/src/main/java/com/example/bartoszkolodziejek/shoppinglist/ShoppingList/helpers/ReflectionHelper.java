package com.example.bartoszkolodziejek.shoppinglist.ShoppingList.helpers;

import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dalvik.system.DexFile;

public class ReflectionHelper {





    public List<URL> getUrls(Context context) throws NoSuchFieldException,  IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        URLClassLoader uc = null;
        DexFile dexFile = new DexFile(context.getPackageCodePath()) ;
        ArrayList<URL> urls = new ArrayList<URL>();


            while (dexFile.entries().hasMoreElements()){
                urls.add(new URL(dexFile.entries().nextElement()));

        }


        ClassLoader [] classloaders = {
                getClass().getClassLoader(),
                Thread.currentThread().getContextClassLoader()
        };
        for (int i = 0; i < classloaders.length; i++) {
            if (classloaders[i] instanceof URLClassLoader) {
                urls.addAll(Arrays.asList(((URLClassLoader)classloaders[i]).getURLs()));
            }
            else {
                throw new RuntimeException("classLoader is not an instanceof URLClassLoader");
            }
        }

        return urls;
    }



}
