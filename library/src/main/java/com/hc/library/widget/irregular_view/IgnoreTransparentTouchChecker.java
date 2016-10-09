package com.hc.library.widget.irregular_view;

import android.graphics.Bitmap;
import android.util.Log;

import com.hc.library.zxing.Recycler;

/**
 * Created by Wqz on 2016/8/29.
 */

public class IgnoreTransparentTouchChecker implements IrregularButton.TouchChecker
{
    private Bitmap bitmap;
    private int bmpH = 0;
    private int bmpW = 0;

    public IgnoreTransparentTouchChecker(Bitmap bitmap){
        this.bitmap = bitmap;

        bmpH = bitmap.getHeight();
        bmpW = bitmap.getWidth();
    }

    @Override
    public boolean isInTouchArea(int x, int y, int width, int height) {

        int conversionX = (x * bmpW) / width;
        int conversionY = (y * bmpH) / height;

        if (bitmap != null) {
            int pixel = bitmap.getPixel(conversionX, conversionY);

            if (((pixel >> 24) & 0xff) > 0)
            {
                Log.d("BitmapTouchChecker", "isInTouchArea return true");
                return true;
            }
        }
        else {
            Log.d("BitmapTouchChecker", "getBitmap is fail !!!");
        }

        Log.d("BitmapTouchChecker", "isInTouchArea return false");
        return false;
    }

}
