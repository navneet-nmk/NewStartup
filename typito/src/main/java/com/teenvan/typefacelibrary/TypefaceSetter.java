package com.teenvan.typefacelibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Created by navneet on 12/02/16.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TypefaceSetter {
    //Declaration of member variables
    String path();
}
