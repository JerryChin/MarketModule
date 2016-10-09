package com.hc.library.util;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Xiong on 2016/9/5.
 */
public final class Compat {
    private Compat(){
        throw new RuntimeException("Stub!");
    }

    public static void removeOnGlobalLayoutListener(View view,ViewTreeObserver.OnGlobalLayoutListener listener){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }else{
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }
}
