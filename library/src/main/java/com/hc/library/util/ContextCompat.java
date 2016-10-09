package com.hc.library.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;

import static android.support.v4.content.ContextCompat.getDrawable;

/**
 * Created by Xiong on 2016/8/21.
 */
public final class ContextCompat {
    private ContextCompat(){}

    public static Drawable[] getDrawableArray(Context context, @ArrayRes int drawableArrayId){
        int[] resIds = getDrawableArrayResources(context,drawableArrayId);
        int len = resIds.length;
        Drawable[] drawables = new Drawable[len];
        for(int i = 0;i < len;i++){
            drawables[i] = getDrawable(context,resIds[i]);
        }

        return drawables;
    }

    public static @DrawableRes int[] getDrawableArrayResources(Context context, @ArrayRes int drawableArrayId){
        TypedArray a = context.getResources().obtainTypedArray(drawableArrayId);
        int len = a.length();
        @DrawableRes int[] resIds = new int[len];
        for(int i = 0;i < len;i++){
            resIds[i] = a.getResourceId(i,0);
        }

        a.recycle();

        return resIds;
    }

}
