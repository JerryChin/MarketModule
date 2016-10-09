package com.hc.library.widget.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by Xiong on 2016/8/17.
 */
public class DrawableBean extends IdBean{
    private int order;
    private Drawable drawable;
    private Context mContext;

    public DrawableBean(Context context) {
        mContext = context;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setDrawable(@DrawableRes int drawableId) {
        drawable = ContextCompat.getDrawable(mContext,drawableId);
    }
}
