package com.hc.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Wqz on 2016/8/26.
 */

public class WeatherView24h extends View
{
    int WEATHER_24H_WIDTH;
    int WEATHER_24H_HEIGHT;

    boolean isSeted = false;
    Context nWeatherFragment;

    int[] Tem = null;
    String[] hours = null;

    public WeatherView24h(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        nWeatherFragment = context;
    }

    public WeatherView24h(Context context, AttributeSet attrs) {
        super(context, attrs);
        nWeatherFragment = context;
    }

    public WeatherView24h(Context context) {
        super(context);
        nWeatherFragment = context;
    }

    public void setAll(int[] Tem,String[] hours)
    {
        this.Tem = Tem;
        this.hours = hours;

        isSeted = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (!isSeted) return;
    }
}
