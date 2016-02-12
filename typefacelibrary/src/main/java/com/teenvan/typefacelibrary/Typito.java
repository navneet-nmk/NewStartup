package com.teenvan.typefacelibrary;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by navneet on 12/02/16.
 */
public class Typito {
    private static final String TAG = Typito.class.getSimpleName();

    public static void typefaceSetterForActivity(Activity activity, String path){
        Field[] fields = activity.getClass().getDeclaredFields();
        Log.d(TAG, fields.length+ "");

        for(Field field: fields){
            Log.d(TAG,field.getName());
            field.setAccessible(true);
            try{
                Class<?> cl = field.getType();
                // To get the value of the member in the object: (object is an instance of MyClass)
                //Object o = field.get(c);

                if (TextView.class.isAssignableFrom(cl)) {
                    TextView value = (TextView) field.get(activity);
                    Typeface tv = Typeface.createFromAsset(activity.getAssets(),path);
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

    public static void typefaceSetterForFragments(Activity activity, Fragment fragment)  {
        Field[] fields = fragment.getClass().getDeclaredFields();
        Log.d(TAG, fields.length+ "");

        for(Field field : fields){
            Log.d(TAG, field.getName());
            field.setAccessible(true);
            TypefaceSetter setter = field.getAnnotation(TypefaceSetter.class);
            if(setter!= null)
            Log.d(TAG, setter.path());
            try{
                Class<?> cl = field.getType();
                // To get the value of the member in the object: (object is an instance of MyClass)
                //Object o = field.get(c);

                if (TextView.class.isAssignableFrom(cl)) {
                    TextView value = (TextView) field.get(fragment);
                    Typeface tv = Typeface.createFromAsset(activity.getAssets(),setter.path());
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

    public static void typefaceSetterForActivity(Activity activity){
        Field[] fields = activity.getClass().getDeclaredFields();
        Log.d(TAG, fields.length+ "");

        for(Field field: fields){
            Log.d(TAG,field.getName());
            field.setAccessible(true);
            TypefaceSetter setter = field.getAnnotation(TypefaceSetter.class);
            try{
                Class<?> cl = field.getType();
                // To get the value of the member in the object: (object is an instance of MyClass)
                //Object o = field.get(c);

                if (TextView.class.isAssignableFrom(cl)) {
                    TextView value = (TextView) field.get(activity);
                    Typeface tv = Typeface.createFromAsset(activity.getAssets(),setter.path());
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

    public static void typefaceSetterForFragments(Activity activity, Fragment fragment, String path)  {
        Field[] fields = fragment.getClass().getDeclaredFields();
        Log.d(TAG, fields.length+ "");

        for(Field field : fields){
            Log.d(TAG, field.getName());
            field.setAccessible(true);
            try{
                Class<?> cl = field.getType();
                // To get the value of the member in the object: (object is an instance of MyClass)
                //Object o = field.get(c);

                if (TextView.class.isAssignableFrom(cl)) {
                    TextView value = (TextView) field.get(fragment);
                    Typeface tv = Typeface.createFromAsset(activity.getAssets(),path);
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

}
