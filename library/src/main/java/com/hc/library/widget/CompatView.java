package com.hc.library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hc.library.R;

/**
 * Created by xc on 2016/9/7.
 */
public class CompatView extends View {

    public CompatView(Context context) {
        this(context,null);
    }

    public CompatView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CompatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CompatView);

        ColorStateList colorStateList = a.getColorStateList(R.styleable.CompatView_tint);

        if(colorStateList == null){
            int tint = a.getColor(R.styleable.CompatView_tint,-1);
            if(tint != -1){
                android.support.v4.graphics.drawable.DrawableCompat.setTint(getBackground(),tint);
            }
        }else{
            ViewCompat.setBackgroundTintList(this,colorStateList);
        }

        a.recycle();
    }
}
