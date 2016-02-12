package com.teenvan.typefacelibrary;

import android.app.Activity;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by navneet on 12/02/16.
 */
public class Typito {
    private static final String TAG = Typito.class.getSimpleName();

    public static void typefaceSetterForActivity(Activity activity, String path){

        AssetManager assetManager = activity.getAssets();
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
                    Typeface tv = Typeface.createFromAsset(assetManager,path);
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the text typeface");
                }
                if(Button.class.isAssignableFrom(cl)){
                    Button value = (Button)field.get(activity);
                    Typeface tv = Typeface.createFromAsset(assetManager,path);
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the button typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

    public static void typefaceSetterForFragments(Activity activity, Fragment fragment)  {
        AssetManager assetManager = activity.getAssets();
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
                    assert setter != null;
                    Typeface tv = Typeface.createFromAsset(assetManager,setter.path());
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the text typeface");
                }
                if(Button.class.isAssignableFrom(cl)){
                    Button value = (Button)field.get(fragment);
                    assert setter != null;
                    Typeface tv = Typeface.createFromAsset(assetManager,setter.path());
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the button typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

    public static void typefaceSetterForActivity(Activity activity){
        AssetManager assetManager = activity.getAssets();
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
                    Typeface tv = Typeface.createFromAsset(assetManager,setter.path());
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the text typeface");
                }
                if(Button.class.isAssignableFrom(cl)){
                    Button value = (Button)field.get(activity);
                    Typeface tv = Typeface.createFromAsset(assetManager, setter.path());
                    value.setTypeface(tv);
                    Log.d(TAG,"Set the button typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

    public static void typefaceSetterForFragments(Activity activity, Fragment fragment,
                                                  String path)  {
        AssetManager assetManager = activity.getAssets();
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
                    Typeface tv = Typeface.createFromAsset(assetManager,path);
                    value.setTypeface(tv);
                    Log.d(TAG, "Set the text typeface");
                }
                if(Button.class.isAssignableFrom(cl)){
                    Button value = (Button)field.get(fragment);
                    Typeface tv = Typeface.createFromAsset(assetManager,path);
                    value.setTypeface(tv);
                    Log.d(TAG,"Set the button typeface");
                }

            }
            catch (IllegalAccessException e){
                Log.d(TAG,e.getMessage());
            }
        }
    }

}
