package com.hc.library.zxing;

import android.graphics.Bitmap;

import com.google.zxing.LuminanceSource;

/**
 * Created by Xiong on 2016/2/14.
 */
public abstract class BaseLuminanceSource extends LuminanceSource {
    protected BaseLuminanceSource(int width, int height) {
        super(width, height);
    }

    public abstract Bitmap renderCroppedGreyscaleBitmap();
}
