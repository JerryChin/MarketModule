package com.wqz.app;

import android.graphics.drawable.Drawable;

import com.hc.library.base.BaseApplication;

/**
 * Created by wqz on 2016/11/4.
 */

public class MarketAPP extends BaseApplication
{
    private Drawable[] carousel;

    @Override
    public void onCreate() {
        carousel = new Drawable[5];
        super.onCreate();
    }

    public Drawable[] getCarousel()
    {
        return carousel;
    }

    public void setCarousel(Drawable[] carousel)
    {
        this.carousel = carousel;
    }
}
