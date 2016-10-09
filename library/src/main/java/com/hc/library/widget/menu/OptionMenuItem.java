package com.hc.library.widget.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.hc.library.util.TitleAction;


/**
 * Created by xc on 2016/8/6.
 */
public class OptionMenuItem implements TitleAction<OptionMenuItem> {
    public final static int NONE = com.hc.library.base.TitleAction.NONE;

    private com.hc.library.base.TitleAction mAction;

    public OptionMenuItem(Context context) {
        mAction = new com.hc.library.base.TitleAction(context);
    }


    @Override
    public OnActionListener getActionListener() {
        return mAction.getActionListener();
    }

    @Override
    public OptionMenuItem setOnActionListener(OnActionListener l) {
        mAction.setOnActionListener(l);
        return this;
    }

    @Override
    public OptionMenuItem setText(@StringRes int resId) {
        mAction.setText(resId);
        return this;
    }

    @Override
    public OptionMenuItem setText(String text) {
        mAction.setText(text);
        return this;
    }

    @Override
    public OptionMenuItem setTextColor(int color) {
        mAction.setTextColor(color);
        return this;
    }

    @Override
    public OptionMenuItem setTextColorResource(@ColorRes int colorRes) {
        mAction.setTextColorResource(colorRes);
        return this;
    }

    @Override
    public int getTextColor() {
        return mAction.getTextColor();
    }

    @Override
    public OptionMenuItem setIcon(@DrawableRes int resId) {
        mAction.setIcon(resId);
        return this;
    }

    @Override
    public OptionMenuItem setIcon(Drawable drawable) {
        mAction.setIcon(drawable);
        return this;
    }

    @Override
    public Drawable getIcon() {
        return mAction.getIcon();
    }

    @Override
    public String getText() {
        return mAction.getText();
    }

    @Override
    public OptionMenuItem setActionView(View view) {
        mAction.setActionView(view);
        return this;
    }

    @Override
    public View getActionView() {
        return mAction.getActionView();
    }

    @Override
    public int getPosition() {
        return mAction.getPosition();
    }

    @Override
    public void onDestroy() {
        mAction.onDestroy();
    }
}
