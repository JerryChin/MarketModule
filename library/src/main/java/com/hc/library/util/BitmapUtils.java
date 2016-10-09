package com.hc.library.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by xc on 2016/9/14.
 */
public final class BitmapUtils {
    private BitmapUtils(){
        throw new RuntimeException("Stub");
    }

    public static void recycleBitmap(Bitmap bitmap){
        if(bitmap != null && bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}

